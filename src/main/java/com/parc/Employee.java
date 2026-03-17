package com.parc;

public class Employee {
    String name;
    int age;
    String email;
    String phone;

    Employee(String name, int age, String email, String phone) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class EmployeeBuilder {
        private String name;
        private int age;
        private String email;
        private String phone;

        EmployeeBuilder() {
        }

        public EmployeeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EmployeeBuilder age(int age) {
            this.age = age;
            return this;
        }

        public EmployeeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Employee build() {
            return new Employee(this.name, this.age, this.email, this.phone);
        }

        public String toString() {
            return "Employee.EmployeeBuilder(name=" + this.name + ", age=" + this.age + ", email=" + this.email + ", phone=" + this.phone + ")";
        }
    }
}
