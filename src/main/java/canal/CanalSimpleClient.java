package canal;

import java.net.InetSocketAddress;
import java.util.List;
//import java.util.stream.Collectors;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

public class CanalSimpleClient {

    public static void main(String[] args) throws Exception {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("192.168.12.132", 11111), "example", "", "");

        connector.connect();
        connector.subscribe(".*\\..*");
        //connector.rollback();

        while (true) {
            Message message = connector.getWithoutAck(1);
            long batchId = message.getId();
            if (batchId == -1 || message.getEntries().isEmpty()) {
                System.out.println("sleep");
                Thread.sleep(3000);
                continue;
            }
            printEntries(message.getEntries());
            connector.ack(batchId);
        }
    }

    private static void printEntries(List<Entry> entries) throws Exception {
        for (Entry entry : entries) {
            if (entry.getEntryType() != EntryType.ROWDATA) {
                continue;
            }

            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            for (RowData rowData : rowChange.getRowDatasList()) {
                switch (rowChange.getEventType()) {
                case INSERT:
                case UPDATE:
                    System.out.print("UPSERT ");
                    printColumns(rowData.getAfterColumnsList());

//                    if ("retl_buffer".equals(entry.getHeader().getTableName())) {
                        String tableName = entry.getHeader().getTableName();
                        String pkValue = rowData.getAfterColumns(0).getValue();
                        System.out.println("SELECT * FROM " + tableName + " WHERE id = " + pkValue);
//                    }
                    break;

                case DELETE:
                    System.out.print("DELETE ");
                    printColumns(rowData.getBeforeColumnsList());
                    break;

                default:
                    break;
                }
            }
        }
    }

    private static void printColumns(List<Column> columns) {
//        String line = columns.stream()
//                .map(column -> column.getName() + "=" + column.getValue())
//                .collect(Collectors.joining(","));
//        System.out.println(line);
        for (Column column : columns) {  
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());  
        }  
    }

}