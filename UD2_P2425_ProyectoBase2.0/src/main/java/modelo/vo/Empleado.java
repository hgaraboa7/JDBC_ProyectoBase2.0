/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.sql.Date;

/**
 *
 * @author hector.garaboacasas
 */
public class Empleado {
    
    public int emp_no;
    
    public String apellido;
    
    public String oficio;
    public int dir;
     public Date date;
     public Double salario;
     public Double comision;
     public int emp_dept_no;

    public int getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public int getDir() {
        
        
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Empleado(int emp_no, String apellido, String oficio, int dir, Date date, Double salario, int emp_dept_no) {
        this.emp_no = emp_no;
        this.apellido = apellido;
        this.oficio = oficio;
        this.dir = dir;
        this.date = date;
        this.salario = salario;
        
        this.emp_dept_no = emp_dept_no;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public int getEmp_dept_no() {
        return emp_dept_no;
    }

    public void setEmp_dept_no(int emp_dept_no) {
        this.emp_dept_no = emp_dept_no;
    }

    @Override
    public String toString() {
        return "Empleado{" + "emp_no=" + emp_no + ", apellido=" + apellido + ", emp_dept_no=" + emp_dept_no + '}';
    }
     
    
    
    
    
}
