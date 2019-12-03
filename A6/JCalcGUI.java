//Bryan Jay
//260738764

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.gui.TableLayout;
import acm.program.Program;

@SuppressWarnings("serial")
public class JCalcGUI extends Program {
	JTextField expression = new JTextField();
	JTextField answer = new JTextField();
	String inExpression = "";
	JSlider slider = new JSlider(1,10);
	JTextField precision = new JTextField();
	int precisionValue = 6;
	
	public static void main(String args[]){
		JCalcGUI app = new JCalcGUI();
		app.run();
	}
	
	//Setup for GUI program, adding all relevant buttons (operators, numbers etc)
	public void setDisplay(){
		setLayout(new TableLayout(8,5)); /*Setting table size for required amount of displays*/
		add(expression,"gridwidth=5");
		add(answer,"gridwidth=5");
		add(new JButton("C"));
		add(new JButton("+/-"));
		add(new JButton("/"));
		add(new JButton("^"));
		add(new JButton(""));
		add(new JButton("7"));
		add(new JButton("8"));
		add(new JButton("9"));
		add(new JButton("x"));
		add(new JButton(")"));
		add(new JButton("4"));
		add(new JButton("5"));
		add(new JButton("6"));
		add(new JButton("-"));
		add(new JButton("("));
		add(new JButton("1"));
		add(new JButton("2"));
		add(new JButton("3"));
		add(new JButton("+"));
		add(new JButton("="));
		add(new JButton("0"));
		add(new JButton("."));
		add(new JLabel("Precision"));
		add(slider);
		add(precision);
		
		precision.setText(Integer.toString(precisionValue));
		
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event){
				precisionValue = slider.getValue();
				precision.setText(Integer.toString(precisionValue));
			}
		});
		
		//Setting size of applet when program launched 
		setSize(400,300);
	}
	
	public void actionPerformed(ActionEvent event){
		String keyPressed = event.getActionCommand();
		
		//Setting purpose of each button
		switch(keyPressed){
		//clears display of calculator
		case "C":
			inExpression = "";
			expression.setText(inExpression);
			answer.setText(inExpression);
			break;
		//gets result of equation	
		case "=":
			expression.setText(inExpression+"=");
			double result = JCalc.compute(inExpression);
			answer.setText(String.format("%1$."+precisionValue+"f", result));
			break;
		case "+/-":
			int length = inExpression.length();
			String temp = inExpression.substring(length - 1);
			int i;
			
			if(JCalc.isNumber(inExpression)) {
				inExpression = "-".concat(inExpression);
			} else {
				for(i = length-1; JCalc.isNumber(temp) || inExpression.charAt(i) == '.'; i--) {
					temp = inExpression.substring(i-1,length-1);
				}

				temp = inExpression.substring(i+1, length);
				inExpression = inExpression.substring(0, i+1);
				inExpression = inExpression.concat("-");
				inExpression = inExpression.concat(temp);
			}

			expression.setText(inExpression);
			break;
			
		default:
			inExpression += keyPressed;
			expression.setText(inExpression);
			break;	
		}
	}
	
	public void run(){
		setDisplay();
		addActionListeners();	
	}
}
