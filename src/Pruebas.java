import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class InterfazConversor extends JFrame implements ActionListener{
	
	JComboBox<String> cobogradosOriginales, comboConvertir;
	JTextField cajaGrados,cajaConvertido;
	String grados[]= {"Elige opcion...","Centigrados","Fahrenheit","Kelvin","Rankine"};
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	public InterfazConversor() {
		getContentPane().setLayout(gbl);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Conversor");
		setLocationRelativeTo(null);
		setVisible(true);
		JLabel label1=new JLabel("Convertir: ");
		llenado(GridBagConstraints.HORIZONTAL, 0, 0, 1, 2, label1);
		cajaGrados=new JTextField(10);
		
		cajaGrados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = cajaGrados.getText();
                int code=ke.getKeyCode();
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')||(!value.contains(".")&&ke.getKeyChar()=='.')||(code==KeyEvent.VK_BACK_SPACE)) {
                	cajaGrados.setEditable(true);
                }else{
                	cajaGrados.setEditable(false);
                }
            }
        });
		cajaGrados.addActionListener(this);
		llenado(GridBagConstraints.HORIZONTAL, 3, 0, 1, 1, cajaGrados);
		cobogradosOriginales=new JComboBox<String>(grados);
		cobogradosOriginales.addActionListener(this);
		llenado(GridBagConstraints.HORIZONTAL, 4, 0, 1, 1, cobogradosOriginales);
		JLabel label2=new JLabel("a:");
		llenado(GridBagConstraints.HORIZONTAL, 0, 1, 1, 2, label2);
		String gradosin[]= {"Elige opcion..."};
		comboConvertir=new JComboBox<String>(gradosin);
		llenado(GridBagConstraints.HORIZONTAL, 2, 1, 1, 2, comboConvertir);
		cajaConvertido=new JTextField(10);
		llenado(GridBagConstraints.HORIZONTAL, 4,1, 1, 2, cajaConvertido);
		pack();
		
	}
	public void llenado(int rellenado,int x, int y,int altura,int largo,Component componente) {
		gbc.gridx= x;
		gbc.gridy=y;
		gbc.gridheight=altura;
		gbc.gridwidth=largo;
		gbc.fill=rellenado;
		gbl.setConstraints(componente, gbc);
		add(componente);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DecimalFormat df = new DecimalFormat("##.00");
		String gradossinC[]= {"Fahrenheit","Kelvin","Rankine"};
		String gradossinF[]= {"Centigrados","Kelvin","Rankine"};
		String gradossinK[]= {"Centigrados","Fahrenheit","Rankine"};
		String gradossinR[]= {"Centigrados","Fahrenheit","Kelvin"};
		Convertor convertor=new Convertor();
		if(e.getSource() == cobogradosOriginales) {
			comboConvertir.removeAllItems();
			if(cobogradosOriginales.getSelectedItem().equals("Centigrados")) {
				for( String x : gradossinC)
					comboConvertir.addItem(x);
			}else if (cobogradosOriginales.getSelectedItem().equals("Fahrenheit")) {
				for( String x : gradossinF)
					comboConvertir.addItem(x);
			}else if (cobogradosOriginales.getSelectedItem().equals("Kelvin")) {
				for( String x : gradossinK)
					comboConvertir.addItem(x);
			}else if (cobogradosOriginales.getSelectedItem().equals("Rankine")) {
				for( String x : gradossinR)
					comboConvertir.addItem(x);
			}
		}
		if(e.getSource()==cajaGrados) {
			if(!(cobogradosOriginales.getSelectedItem().equals("Elige opcion...") || 
					comboConvertir.getSelectedItem().equals("Elige opcion...") )) {
				if(cobogradosOriginales.getSelectedItem().equals("Centigrados") && 
						comboConvertir.getSelectedItem().equals("Fahrenheit")){
					double gradosC=Double.parseDouble(cajaGrados.getText());
					//System.out.println(gradosC);
					cajaConvertido.setText(String.valueOf(convertor.caF(gradosC)));
				}else if(cobogradosOriginales.getSelectedItem().equals("Centigrados") &&
						comboConvertir.getSelectedItem().equals("Kelvin")){
					double gradosC=Double.parseDouble(cajaGrados.getText());
					cajaConvertido.setText(String.valueOf(convertor.caK(gradosC))+"°");
				}else if(cobogradosOriginales.getSelectedItem().equals("Centigrados") &&
						comboConvertir.getSelectedItem().equals("Rankine")){
					double gradosC=Double.parseDouble(cajaGrados.getText());
					cajaConvertido.setText(String.valueOf(df.format(convertor.caR(gradosC)))+"°");
				}else if(cobogradosOriginales.getSelectedItem().equals("Fahrenheit") &&
						comboConvertir.getSelectedItem().equals("Centigrados")){
					double gradosC=Double.parseDouble(cajaGrados.getText());
					gradosC=convertor.FaC(gradosC);
					cajaConvertido.setText(String.valueOf(df.format(gradosC))+"°");
				}
			}
		}//Caja
		
		
		}//Eventos
	}//Class
	

class Convertor{
	
	public double FaC(double gradosF) {
		return (gradosF-32)/1.8000;
	}
	public double KaC(double gradosk) {
		return gradosk-273.15;
	}
	public double RaC(double gradosR) {
		return (gradosR-491.67)/1.8000;
	}
	
	public double caF(double gradosC) {
		return gradosC*1.800+32;
	}
	public double caK(double gradosC) {
		return gradosC+273.15;
	}
	public double caR(double gradosC) {
		return gradosC*1.800+491.67;
	}
	
}
public class Pruebas {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new InterfazConversor();
			}
		});

	}

}
