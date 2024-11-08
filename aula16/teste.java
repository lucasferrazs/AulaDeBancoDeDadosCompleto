Usar as funcionalidades de procedures e views nos ajuda a esconder as nossas tabelas no 
código fonte da linguagem , o que serve como uma proteção aos dados que estão ali armazenados
veja a diferença entre um código em java usando procedure e não usando:


Usando procedure 

import java.sql.*;

public class ExemploProcedure {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/seu_banco",
                "usuario",
                "senha"
            );

            PreparedStatement ps = conn.prepareStatement("CALL AdicionarFuncionario(?, ?, ?, ?)");
            
            ps.setString(1, "João Silva");
            ps.setString(2, "1234-5678");
            ps.setDate(3, java.sql.Date.valueOf("1990-01-01"));
            ps.setDouble(4, 3000.00);

            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



Não usando Procedures

import java.sql.*;

public class ExemploInsert {
    public static void main(String[] args) {
        // Dados de conexão
        String url = "jdbc:mysql://localhost:3306/seu_banco";
        String usuario = "seu_usuario";
        String senha = "sua_senha";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            // SQL para inserir um funcionário
            String sql = "INSERT INTO funcionarios (nome, telefone, datanascimento, salario) VALUES (?, ?, ?, ?)";
            
            // Criar PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Definir os valores dos parâmetros
            pstmt.setString(1, "João Silva");
            pstmt.setString(2, "11999999999");
            pstmt.setDate(3, java.sql.Date.valueOf("1990-05-15"));
            pstmt.setDouble(4, 5000.00);
            
            // Executar o INSERT
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Funcionário inserido com sucesso! Linhas afetadas: " + linhasAfetadas);
            
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


Como voce pode ver , a tabela funcionarios e todos os campos ficaram expostos , sem segurança nenhuma 
para o sistema e para o banco de dados também , e essa lógica tambem se aplica para as views