/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase;

import conexion.*;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import conexion.conexionmysql;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
/**
 *
 * @author alexa
 */
public class Cliente2 {
    
        Connection cn;
    
        
        public void ModificarCliente(JTextField Usuario, JTextField Nombre, JTextField Apellido, JTextField Edad, JTextField Contraseña){
            try {
                conexionmysql con = new conexionmysql();
                cn=con.conectar();
                
                String sql = "CALL MODIFICAR_TRABAJADOR (?,?,?,?,?)";
                
                PreparedStatement pst = cn.prepareCall(sql);
                
                pst.setString(1, Usuario.getText());
                pst.setString(2, Nombre.getText());
                pst.setString(3, Apellido.getText());
                pst.setString(4, Edad.getText());
                pst.setString(6, Contraseña.getText());
                
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "SE HA ACTUALIZADO EL CIENTE");
                pst.close();
                cn.close();
            } catch (SQLException ex) {
                System.out.println("Se ha actualizado la tabla");
            }
        }
        
        
        
        
        
    public void CargarTabla(JTable tabla, String cadena){
        DefaultTableModel modelo;
        String [] titulo = {"USUARIO","NOMBRE(S)","APELLIDOS","EDAD","CARGO","CONTRASEÑA"};
        modelo = new DefaultTableModel(null, titulo);
        
        String [] registros = new String[6];
        String sql = "SELECT * FROM usuarios WHERE CONCAT(Usuario,' ', Nombre,' ', Apellido,' ', Edad,' ', Cargo,' ', Contraseña) LIKE '%"+cadena+"%'";
        conexionmysql con = new conexionmysql();
        cn = con.conectar();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                for(int i=0;i<6;i++)
                    registros[i]=rs.getString(i+1);
                    modelo.addRow(registros);
            }
            tabla.setModel(modelo);
         }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error: "+ex);
         }
    
    }
    
}
