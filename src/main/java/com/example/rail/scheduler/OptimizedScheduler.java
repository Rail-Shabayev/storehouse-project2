package com.example.rail.scheduler;


import com.example.rail.annotation.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;

@Slf4j
@Component
@Profile("!local")
@ConditionalOnExpression("${app.scheduling.enabled} and " +
        "${app.scheduling.optimization}")
@RequiredArgsConstructor
public class OptimizedScheduler {
    private static final int BATCH_SIZE = 10000;

    private final DataSource dataSource;

    @Value("${app.scheduling.priceIncreasePercentage}")
    private BigDecimal percentage;

    @Value("${app.scheduling.optimize.exclusive-lock:false}")
    private Boolean exclusiveLock;

    @Scheduled(fixedDelayString = "${app.scheduling.fixedDelay}")
    @TrackExecutionTime
    @Transactional
    public void optimizedIncreasePriceScheduler() {
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            if (exclusiveLock) {
                String lock = "LOCK TABLE product IN ACCESS EXCLUSIVE MODE";
                Statement lockStatement = conn.createStatement();
                lockStatement.execute(lock);
            }
            String query = "SELECT * FROM product FOR UPDATE";
            PreparedStatement updateStmt =
                    conn.prepareStatement("UPDATE product SET price = ? WHERE uuid= ?");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/file.txt"));
            int count = 0;
            while (rs.next()) {
                bw.write(buildString(rs, count));
                bw.newLine();
                BigDecimal price = rs.getBigDecimal("price");
                price = price.multiply(BigDecimal.ONE.add(percentage.
                        divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)));

                updateStmt.setBigDecimal(1, price);
                updateStmt.setObject(2, rs.getObject("uuid"));
                updateStmt.addBatch();

                if (++count % BATCH_SIZE == 0) {
                    log.info("Products processed: {}", count);
                    updateStmt.executeBatch();
                }
            }
            updateStmt.executeBatch();
            conn.commit();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildString(ResultSet resultSet, int counter) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append(counter).append(" ");
        builder.append(resultSet.getString("uuid")).append(" ");
        builder.append(resultSet.getString("name")).append(" ");
        builder.append(resultSet.getString("description")).append(" ");
        builder.append(resultSet.getBigDecimal("price")).append(" ");
        builder.append(resultSet.getString("article"));

        return builder.toString();
    }
}


