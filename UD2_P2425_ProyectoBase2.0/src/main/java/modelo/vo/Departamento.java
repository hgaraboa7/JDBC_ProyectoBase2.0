/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author hector.garaboacasas
 */
public class Departamento {
    public int dept_no;
    public String nombre;
    public String loc;

    public int getDept_no() {
        return dept_no;
    }

    public void setDept_no(int dept_no) {
        this.dept_no = dept_no;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Departamento(int dept_no, String nombre, String loc) {
        this.dept_no = dept_no;
        this.nombre = nombre;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    
    
    
}
