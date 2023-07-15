package com.lsh.guava.entity;

import com.google.common.base.MoreObjects;

/**
 * @Author lishaohui
 * @Date 2023/6/10 11:17
 */
public class Employee {

    private final String name;

    private final String dept;

    private final String empId;

    private final byte[] data = new byte[1024 * 1024];

    public Employee(String name, String dept, String empId) {
        this.name = name;
        this.dept = dept;
        this.empId = empId;
    }


    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getEmpId() {
        return empId;
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("Name", this.getName())
                .add("Department", this.getDept())
                .add("EmployeeId", this.getEmpId())
                .toString();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("The name: " + getName() + " will be gc.");
    }
}
