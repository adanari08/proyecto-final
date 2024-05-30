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
import conexion.conexionmysql2;
/**
 *
 * @author alexa
 */
public class Cliente {
    
        Connection cn;
    
    public void CargarTabla(JTable tabla, String cadena){
        DefaultTableModel modelo;
        String [] titulo = {"NUC","NOMBRE(S)","APELLIDOS","EDAD","DELITO","LUGAR_NACIMIENTO","TIPO_SANGRE"};
        modelo = new DefaultTableModel(null, titulo);
        
        String [] registros = new String[7];
        String sql = "SELECT * FROM presos WHERE CONCAT(rnd,' ', nombre,' ', apellido,' ', edad,' ', delito,' ', lugarNacimiento,' ', tipoSangre) LIKE '%"+cadena+"%'";
        conexionmysql2 con = new conexionmysql2();
        cn = con.conectar();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                for(int i=0;i<7;i++)
                    registros[i]=rs.getString(i+1);
                    modelo.addRow(registros);
            }
            tabla.setModel(modelo);
         }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error: "+ex);
         }
        
    }
    
}
