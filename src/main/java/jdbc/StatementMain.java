package jdbc;

import java.sql.*;

public class StatementMain {

    public void showUser(){
        //���ݿ�����
        Connection connection = null;
        //Ԥ�����Statement��ʹ��Ԥ�����Statement������ݿ�����
        PreparedStatement preparedStatement = null;
        Statement normalStatement = null;
//        normalStatement.
        //��� ��
        ResultSet resultSet = null;

        try {
            //�������ݿ�����
            Class.forName("com.mysql.jdbc.Driver");

            //ͨ�������������ȡ���ݿ�����
            connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?&useServerPrepStmts=true&cachePrepStmts=true", "root", "");

            preparedStatement=connection.prepareStatement("select * from user where username like ?");
            preparedStatement.setString(1, "%С��%");
            resultSet =  preparedStatement.executeQuery();
            //������ѯ�����
            while(resultSet.next()){
                System.out.println(resultSet.getString("id")+"  "+resultSet.getString("username"));
            }
            //ע���������Ҫ�رյ�ǰPreparedStatement�������������´��ٴδ���PreparedStatement�����ʱ���ǻ��ٴ�Ԥ����sqlģ�壬ʹ��PreparedStatement����󲻹رյ�ǰPreparedStatement�������ǲ��Ỻ��Ԥ�����ĺ���key��
            resultSet.close();
            preparedStatement.close();

            preparedStatement=connection.prepareStatement("select * from user where username like ?");
            preparedStatement.setString(1, "%��%");
            resultSet =  preparedStatement.executeQuery();
            //������ѯ�����
            while(resultSet.next()){
                System.out.println(resultSet.getString("id")+"  "+resultSet.getString("username"));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
