package io.github.recursivejr.discenteVivo.dao.postgres;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.FormularioDaoInterface;
import io.github.recursivejr.discenteVivo.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormularioDaoPostgres extends ElementoDao implements FormularioDaoInterface {

    public FormularioDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Integer adicionar(Formulario formulario) {
        Integer idFormulario = null;

        String sql = "INSERT INTO Formulario (Nome, Descricao, EmailAdmin, Foto) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, formulario.getNome());
            stmt.setString(2, formulario.getDescricao());
            stmt.setString(3, formulario.getEmailAdmin());
            stmt.setString(4, formulario.getFoto());

            stmt.executeUpdate();

            //Recupera o valor do Id deste Formulario no BD para ser usado nas proximas Querys
            idFormulario = buscarId(formulario.getNome());

            //Verifica se Ha Campos que devem ser Salvos neste Formulario
                //Se nao for null entao ha Campos que devem ser referenciadas
            if (formulario.getCampos() != null) {

                //Cria um DaoPostgres para salvar os Campos
                CampoDaoPostgres campoDao = new CampoDaoPostgres();

                //Percorre todos os campos salvando eles no BD
                for (Campo campo : formulario.getCampos()) {
                    campo.setIdFormulario(idFormulario);
                    campoDao.adicionar(campo);
                }
            }

            //Cria um ArrayList de Cursos e Verifica se Ha Cursos que devem ser Referenciados
                // por este Formulario
            List<Curso> cursos = new ArrayList<>();

            //Se nao for null entao ha Cursos que devem ser Referenciados
            if (formulario.getCursos() != null) {
                //Recupera todas os Cursos que serao Referenciados
                cursos.addAll(formulario.getCursos());

                sql = "INSERT INTO FormularioCurso (idFormulario, NomeCurso) VALUES (?,?);";
                stmt = getConexao().prepareStatement(sql);

                //Perccore todas os Cursos salvando eles no BD
                for (Curso curso : cursos) {
                    stmt.setInt(1, idFormulario);
                    stmt.setString(2, curso.getNome());
                    stmt.executeUpdate();
                }
            }

            //Cria um ArrayList de Setores e Verifica se Ha Setores que devem ser
                // Referenciados neste Formulario
            List<Setor> setores = new ArrayList<>();

            //Se nao for null entao ha Setores que devem ser Referenciados
            if (formulario.getSetores() != null) {
                //Recupera todas os Setores que serao Referenciados
                setores.addAll(formulario.getSetores());

                sql = "INSERT INTO FormularioSetor (idFormulario, NomeSetor) VALUES (?,?);";
                stmt = getConexao().prepareStatement(sql);

                //Percorre todas os Setores salvando eles no BD
                for (Setor setor : setores) {
                    stmt.setInt(1, idFormulario);
                    stmt.setString(2, setor.getNome());
                    stmt.executeUpdate();
                }
            }
            
            stmt.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return idFormulario;
    }

    @Override
    public boolean remover(int idFormulario) {

        String sql = "DELETE FROM FormularioSetor WHERE IdFormulario = ?;"
                + "DELETE FROM FormularioCurso WHERE IdFormulario = ?;"
                + "DELETE FROM ComentaFormulario WHERE IdFormulario = ?;"
                + "DELETE FROM RespondeCampo WHERE idCampo IN (SELECT IdCampo FROM Campo WHERE idFormulario = ?);"
                + "DELETE FROM OpcoesCampo WHERE idCampo IN (SELECT IdCampo FROM Campo WHERE idFormulario = ?);"
                + "DELETE FROM Campo WHERE IdFormulario = ?;"
                + "DELETE FROM Formulario WHERE IdFormulario = ?;";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, idFormulario);
            stmt.setInt(2, idFormulario);
            stmt.setInt(3, idFormulario);
            stmt.setInt(4, idFormulario);
            stmt.setInt(5, idFormulario);
            stmt.setInt(6, idFormulario);
            stmt.setInt(7, idFormulario);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Formulario formulario) {
        String sql = "DELETE FROM FormularioSetor WHERE IdFormulario = ?;"
                + "DELETE FROM FormularioCurso WHERE IdFormulario = ?;"
                + "UPDATE Formulario SET Nome = ?, Descricao = ?, emailAdmin = ? WHERE IdFormulario = ?;";


        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setInt(1, formulario.getId());
            stmt.setInt(2, formulario.getId());
            stmt.setString(3, formulario.getNome());
            stmt.setString(4, formulario.getDescricao());
            stmt.setString(5, formulario.getEmailAdmin());
            stmt.setInt(6, formulario.getId());


            stmt.executeUpdate(sql);

            sql = "INSERT INTO FormularioCurso(NomeCurso, IdFormulario) VALUES (?,?);";
            PreparedStatement stmtCurso = getConexao().prepareStatement(sql);
            for (Curso curso : formulario.getCursos()) {
                stmtCurso.setString(1, curso.getNome());
                stmtCurso.setInt(2, formulario.getId());
                stmtCurso.executeUpdate(sql);
            }

            sql = "INSERT INTO FormularioSetor(NomeSetor, IdFormulario) VALUES (?,?);";
            PreparedStatement stmtSetor = getConexao().prepareStatement(sql);
            for (Setor setor : formulario.getSetores()) {
                stmtSetor.setString(1, setor.getNome());
                stmtSetor.setInt(2, formulario.getId());
                stmtSetor.executeUpdate(sql);
            }

            stmt.close();
            stmtCurso.close();
            stmtSetor.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Formulario> listar() {
        //Retorna todas os Formularios Salvas

        String sql = "SELECT * FROM Formulario;";
        return getAllFormularios(sql);
    }

    @Override
    public List<Formulario> listarPorAluno(String matAluno) {
        //Retorna apenas os Formularios que nao tem nenhum curso pois sao para toda a universidade
        //e os Formularios do seu curso especifico

        String sql = String.format("SELECT F.* FROM Formulario F NATURAL LEFT JOIN FormularioCurso FC" +
                " WHERE FC.NomeCurso IS NULL OR FC.NomeCurso ILIKE" +
                " (SELECT NomeCurso FROM Aluno WHERE Matricula ILIKE '%s');", matAluno);
        return getFormularios(sql, matAluno);
    }


    @Override
    public Formulario buscar(int idFormulario, String matAluno) {

        //Testar se n da erro ao tentar buscar um Formulario q nao existe

        String sql = String.format("SELECT * FROM Formulario WHERE idFormulario = '%d';", idFormulario);

        List<Formulario> formularios = null;

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            formularios = getAllFormularios(sql);
        else
            //caso matAluno nao seja null entao filtra por aluno
            formularios = getFormularios(sql, matAluno);

        if (formularios.isEmpty()) {
            return null;
        } else {
            return formularios.get(0);
        }
        
    }

    @Override
    public List<Formulario> formulariosPorSetor(String nomeSetor, String matAluno) {

        String sql = String.format("SELECT F.* FROM Formulario F NATURAL JOIN FormularioSetor FS " +
                "WHERE FS.nomeSetor ILIKE '%s';", nomeSetor);

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            return getAllFormularios(sql);
        else
            //caso matAluno nao seja null entao filtra por aluno
            return getFormularios(sql, matAluno);
    }

    @Override
    public List<Formulario> formulariosPorCurso(String nomeCurso, String matAluno) {

        String sql = String.format("SELECT F.* FROM Formulario F NATURAL JOIN FormularioCurso FC " +
                "WHERE FC.nomeCurso ILIKE '%s';", nomeCurso);

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            return getAllFormularios(sql);
        else
            //caso matAluno nao seja null entao filtrar por aluno
            return getFormularios(sql, matAluno);
    }

    @Override
    public boolean atualizarFoto(String foto, int idFormulario) {
        String sql = "UPDATE Formulario SET Foto = ? WHERE idFormulario = ?";

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, foto);
            stmt.setInt(2, idFormulario);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public String retornarFoto(int idFormulario) {
        String foto = null;
        String sql = String.format("SELECT Foto FROM Formulario WHERE idFormulario = %d", idFormulario);

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
                foto = rs.getString("Foto");

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foto;
    }

    private int buscarId(String nome) {
        String sql = String.format("SELECT idFormulario FROM Formulario WHERE nome ILIKE '%s';", nome);
        int aux = -1;
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                aux = rs.getInt("idFormulario");
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aux;
    }

    private List<Formulario> getFormularios(String sql, String matAluno){
        List<Formulario> formularios= new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                List<Comentario> comentarios = new ArrayList<>();
                List<Campo> campos = new ArrayList<>();
                List<Curso> cursos = new ArrayList<>();
                List<Setor> setores = new ArrayList<>();

                Formulario formulario = new Formulario();
                formulario.setId(rs.getInt("idFormulario"));
                formulario.setNome(rs.getString("nome"));
                formulario.setFoto(rs.getString("foto"));
                formulario.setDescricao(rs.getString("descricao"));
                formulario.setEmailAdmin(rs.getString("emailAdmin"));

                Statement internalStmt = getConexao().createStatement();

                //Percorre todos os comentarios e recupera apenas o comentario deste aluno
                String sqlComentarios = String.format("SELECT * FROM ComentaFormulario WHERE IdFormulario = " +
                        "'%d' and MatriculaAluno ILIKE '%s';", formulario.getId(), matAluno);
                ResultSet rsLista = internalStmt.executeQuery(sqlComentarios);
                while (rsLista.next()) {

                    Comentario comentario = new Comentario(
                            rsLista.getInt("IdFormulario"),
                            rsLista.getString("MatriculaAluno"),
                            rsLista.getString("Comentario")
                    );

                    comentarios.add(comentario);
                }
                formulario.setComentarios(comentarios);

                //Percorre todos os Campos e adiciona cada um ao Arraylist deste Formulario
                String sqlOpcoes = String.format("SELECT * FROM Campo WHERE IdFormulario = '%d';",
                        formulario.getId());

                rsLista = internalStmt.executeQuery(sqlOpcoes);
                while (rsLista.next()) {

                    //NAO RETORNA AS OPCOES E RESPOSTAS POIS NAO CONCIDERO UTIL RETORNAR ESTES DADOS
                        //NESSE METODO JA QUE NAO SAO NECESSARIOS PARA IDENTIFICAR UM CAMPO DO FORMULARIO
                    Campo campo = new Campo(
                            rs.getInt("idCampo"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("foto"),
                            rs.getInt("IdFormulario"),
                            null,
                            null
                    );

                    campos.add(campo);
                }
                formulario.setCampos(campos);

                //Percorre todos os Cursos Associados a este Formulario e adiciona-os
                    //ao ArrayList de Retorno
                String sqlCursos = String.format("SELECT NomeCurso FROM FormularioCurso" +
                        " WHERE idFormulario = '%d';", formulario.getId());
                rsLista = internalStmt.executeQuery(sqlCursos);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                        //para se identificar a qual Curso pertence o Formulario
                    Curso curso = new Curso();
                    curso.setNome(rsLista.getString("NomeCurso"));

                    cursos.add(curso);
                }
                formulario.setCursos(cursos);

                //Percorre todos os Setores Associados a este Formulario e adiciona-os
                    //ao ArrayList de Retorno
                String sqlSetores = String.format("SELECT NomeSetor FROM FormularioSetor" +
                        " WHERE idFormulario = '%d';", formulario.getId());
                rsLista = internalStmt.executeQuery(sqlSetores);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                        //para se identificar a qual Setor pertence o Formulario
                    Setor setor = new Setor();
                    setor.setNome(rsLista.getString("NomeSetor"));

                    setores.add(setor);
                }
                formulario.setSetores(setores);

                formularios.add(formulario);
                internalStmt.close();
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return formularios;
    }

    private List<Formulario> getAllFormularios(String sql){
        List<Formulario> formularios = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                List<Comentario> comentarios = new ArrayList<>();
                List<Campo> campos = new ArrayList<>();
                List<Curso> cursos = new ArrayList<>();
                List<Setor> setores = new ArrayList<>();

                Formulario formulario = new Formulario();
                formulario.setId(rs.getInt("idFormulario"));
                formulario.setNome(rs.getString("nome"));
                formulario.setFoto(rs.getString("foto"));
                formulario.setDescricao(rs.getString("descricao"));
                formulario.setEmailAdmin(rs.getString("emailAdmin"));

                Statement internalStmt = getConexao().createStatement();

                //Percorre todos os comentarios e adiciona cada um ao Arraylist deste Formulario
                String sqlComentarios = "SELECT * FROM ComentaFormulario WHERE idFormulario = " +
                        "'" + formulario.getId() + "';";
                ResultSet rsLista = internalStmt.executeQuery(sqlComentarios);
                while (rsLista.next()) {

                    Comentario comentario = new Comentario(
                            rsLista.getInt("IdFormulario"),
                            rsLista.getString("MatriculaAluno"),
                            rsLista.getString("Comentario")
                    );

                    comentarios.add(comentario);
                }
                formulario.setComentarios(comentarios);

                //Percorre todas os Campos e adiciona cada um ao Arraylist deste Formulario
                String sqlOpcoes = "SELECT * FROM Campo WHERE idFormulario = '"
                        + formulario.getId() + "';";
                rsLista = internalStmt.executeQuery(sqlOpcoes);
                while (rsLista.next()) {

                    //NAO RETORNA AS OPCOES E RESPOSTAS POIS NAO CONCIDERO UTIL RETORNAR ESTES DADOS
                    //NESSE METODO JA QUE NAO SAO NECESSARIOS PARA IDENTIFICAR UM CAMPO DO FORMULARIO
                    Campo campo = new Campo(
                            rs.getInt("idCampo"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("foto"),
                            rs.getInt("IdFormulario"),
                            null,
                            null
                    );

                    campos.add(campo);
                }
                formulario.setCampos(campos);

                //Percorre todos os Cursos Associados a este Formulario e adiciona-os
                    //ao ArrayList de Retorno
                String sqlCursos = "SELECT NomeCurso FROM FormularioCurso WHERE idFormulario = " +
                        "'" + formulario.getId() +"';";
                rsLista = internalStmt.executeQuery(sqlCursos);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                    //para se identificar a qual Curso pertence ao Formulario
                    Curso curso = new Curso();
                    curso.setNome(rsLista.getString("NomeCurso"));

                    cursos.add(curso);
                }
                formulario.setCursos(cursos);

                //Percorre todos os Setores Associados a este Formulario e adiciona-os
                    //ao ArrayList de Retorno
                String sqlSetores = "SELECT NomeSetor FROM FormularioSetor WHERE idFormulario = " +
                        "'" + formulario.getId() + "';";
                rsLista = internalStmt.executeQuery(sqlSetores);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                    //para se identificar a qual Setor pertence ao Formulario
                    Setor setor = new Setor();
                    setor.setNome(rsLista.getString("NomeSetor"));

                    setores.add(setor);
                }
                formulario.setSetores(setores);

                formularios.add(formulario);
                internalStmt.close();
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return formularios;
    }
}
