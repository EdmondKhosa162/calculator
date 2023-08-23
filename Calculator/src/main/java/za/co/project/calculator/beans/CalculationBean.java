package za.co.project.calculator.beans;

import za.co.project.calculator.ejb.CalculationEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CalculationBean {
    private double operand1;
    private double operand2;
    private String operator;
    private String equation;
    private double result;

    @EJB
    private CalculationEJB calculationEJB;

    public void calculate() {
        result = calculationEJB.calculateBasic(operand1, operand2, operator);
    }

    public void calculateAdvanced() {
        result = calculationEJB.calculateAdvanced(equation);
    }

    public double getOperand1() {
        return operand1;
    }

    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public CalculationEJB getCalculationEJB() {
        return calculationEJB;
    }

    public void setCalculationEJB(CalculationEJB calculationEJB) {
        this.calculationEJB = calculationEJB;
    }
}