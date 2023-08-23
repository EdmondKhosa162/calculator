package za.co.project.calculator.ejb;


import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Stack;

@Stateless
public class CalculationEJB {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @EJB
    private AuditLogEJB auditLogEJB;

    @RolesAllowed("USER")
    public double calculateBasic(double operand1, double operand2, String operator) {
        double result = 0.0;

        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    // Handle division by zero error
                    throw new ArithmeticException("Division by zero");
                }
                break;
            default:
                // Handle invalid operator error
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }

        auditLogEJB.recordCalculation("Basic", String.format("%.2f %s %.2f = %.2f", operand1, operator, operand2, result));
        return result;
    }

    @RolesAllowed("ADMIN")
    public double calculateAdvanced(String equation) {
        try {
            equation = equation.replaceAll("\\s+", "");

            // Evaluate the equation using the built-in JavaScript engine
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
            Object result = engine.eval(equation);

            if (result instanceof Double) {
                // Record calculation in the database
                auditLogEJB.recordCalculation("Advanced", equation + " = " + result);
                return (Double) result;
            } else {
                throw new IllegalArgumentException("Invalid equation");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; // Return NaN on error
        }


    }
}
