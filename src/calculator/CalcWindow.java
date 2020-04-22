package calculator;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

public class CalcWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfResult; // Строка отображающая результат
	private JLabel lbOperation; // Для отображения последней операции
	private JMenuBar menuBar;
	private JMenu mMenu;
	private JMenuItem item1;
	private JMenuItem item2;
	private int operation; // Код операции
	private JButton[] btn;
	private Listener listener;
	private String textResult; // результат, который выводится в текстовое поле
	private double number1; // Первое введенное число
	private double number2; // Второе введенное число
	private String[] btnLabels = { "C", "<", "xʸ", "√", 
								   "7", "8", "9", "/", 
								   "4", "5", "6", "*", 
								   "1", "2", "3", "+",
								   "0", ".", "=", "-" };

	public CalcWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 336, 402);

        listener = new Listener();

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        Font menuFont = new Font("serif", Font.BOLD, 15);
        mMenu = new JMenu("Меню");
        menuBar.add(mMenu);
        item1 = new JMenuItem("Справка");
        item1.setFont(menuFont);
        item1.addActionListener(listener);
        mMenu.add(item1);
        item2 = new JMenuItem("Выход");
        item2.setFont(menuFont);
        item2.addActionListener(listener);
        mMenu.add(item2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tfResult = new JTextField();
        tfResult.setBounds(0, 28, 321, 52);
        tfResult.setFont(new Font("serif", Font.BOLD, 30));
        tfResult.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(tfResult);

        lbOperation = new JLabel("");
        lbOperation.setBounds(0, 12, 318, 15);
        lbOperation.setFont(new Font("serif", Font.BOLD, 18));
        lbOperation.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(lbOperation);

        btn = new JButton[20];
        Font btnFont = new Font("serif", Font.BOLD, 22); // Шрифт для кнопок
        int x = 0, y = 90;//Первоначальные координаты кнопок
        for (int i = 0; i < btn.length; i++) { // Циклом расставляем кнопки, применяем цвета назначаем слушателя
            btn[i] = new JButton();
            btn[i].setLocation(x, y);
            btn[i].setSize(80, 50);
            btn[i].setFont(btnFont);
            btn[i].setText(btnLabels[i]);
            if (i >= 4 && i <= 6 || i >= 8 && i <= 10 || i >= 12 && i <= 14 || i == 16){
                btn[i].setBackground(Color.darkGray);
                btn[i].setForeground(Color.white);
            } else if (i == 18) {
                btn[18].setBackground(Color.blue);
                btn[18].setForeground(Color.white);
            } else {
                btn[i].setBackground(Color.gray);
                btn[i].setForeground(Color.white);
            }
            btn[i].setFocusPainted(false);

            x += 80;
            if ((i + 1) % 4 == 0) { // переход на следуюзую строки при расставлении кнопок.
                x = 0;
                y += 50;
            }
            btn[i].addActionListener(listener); // Создаем слушателя событий
            contentPane.add(btn[i]); // добавляем кнопки
        }
    }

	class Listener implements ActionListener {
		private int position; // позиция знака операции, чтобы парсить второе введенное число
		private JButton btn;
		private String text;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item1) {
				String info = "Гид по операциям:\n\n\"xʸ\" - возведение в степень(ввести число, нажать \"xʸ\", ввести число степени)\n"
						+ "\"√\" - квадратный корень из введенного числа(ввести число, нажать \"√\")\n\"/\" - деление(ввести число, нажать \"/\", ввести второе число, нажать \"=\")"
						+ "\n\"*\" - умножение(ввести число, нажать \"*\", ввести второе число, нажать \"=\")\n\"+\" - сложение(ввести число, нажать \"+\", ввести второе число, нажать \"=\")"
						+ "\n\"-\" - вычитание(ввести число, нажать \"-\", ввести второе число, нажать \"=\")\n\n\"C\" - очищает поле\n\"<\" - удаляет последний символ в поле\n";
				JOptionPane.showMessageDialog(null, info);
			} else if (e.getSource() == item2) {
				System.exit(0);
			} else {
				btn = (JButton) e.getSource();
				text = btn.getText();
				if (text.equalsIgnoreCase("C")) { // Очищаем текстовое поле
					tfResult.setText("");
					number1 = 0;
					number2 = 0;

				} else if (text.equals("<")) { // После нажатия кнопки удаляем из строки последний символ
					isNull(tfResult.getText());
					tfResult.setText(tfResult.getText().substring(0, tfResult.getText().length() - 1));

				} else if (text.equals("+")) {
					operation = 1;
					concatTextField(); // Строим в текстовом поле строку с вычислениями (для наглядности операции)

				} else if (text.equals("-")) {
					operation = 2;
					concatTextField();

				} else if (text.equals("*")) {
					operation = 3;
					concatTextField();

				} else if (text.equals("/")) {
					operation = 4;
					concatTextField();

				} else if (text.equals("xʸ")) {
					operation = 5;
					concatTextField();

				} else if (text.equals("√")) {
					operation = 6;
					number1 = Double.parseDouble(tfResult.getText());
					textResult = runOperation();
					lbOperation.setText("√" + tfResult.getText() + "=" + textResult); // Сохраняем последнюю операцию
					tfResult.setText(textResult);

				} else if (text.equals("=")) {
					String operands = tfResult.getText(); // сохраняем текст до нажатия на кнопку "="
					number2 = Double.parseDouble(tfResult.getText().substring(position, tfResult.getText().length()));
					textResult = runOperation();
					lbOperation.setText(operands + "=" + textResult); // Сохраняем последнюю операцию
					tfResult.setText(textResult);

				} else {
					tfResult.setText(tfResult.getText() + text);
				}
			}
		}

		private void isNull(String text) { // Метод проверяет, есть ли в текстовом поле что то набранное перед
											// совершением операции
			if (text.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Нет введенного числа"); // Если нет то выводит предупреждение
			}
		}

		private boolean isFractNum(double num) { // Метод проверяет дробную часть числа
			double fractionPart = num - (int) num;
			return fractionPart > 0; // возвращает true, если дробная часть > 0, иначе false
		}

		private void concatTextField() { // собираем текст в текстовом поле
			isNull(tfResult.getText()); 
			number1 = Double.parseDouble(tfResult.getText()); // Парсим первое введенное число
			tfResult.setText(tfResult.getText() + (text.equals("xʸ") ? "^" : text)); // Устанавливаем текст: первое число и знак операции (symb)
			position = tfResult.getText().length(); // устанавливаем позицию знака операции. В дальнейшем, при парсинге
													// второго числа, отталкиваемся от этой позиции
		}
		
		private String runOperation() { // Метод совершает вычисление в зависимости от выбранной операции. Возвращает отформатированную строку, которую передаем в текстовое поле как результат
			double result = 0;
			switch (operation) {
				case 1:
					result = number1 + number2;
					break;
				case 2:
					result = number1 - number2;
					break;
				case 3:
					result = number1 * number2;
					break;
				case 4:
					if (number2 == 0) {
						try {
							throw new Exception("На 0 делить нельзя!");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					result = number1 / number2;
					break;
				case 5:
					result = Math.pow(number1, number2);
					break;
				case 6:
					result = Math.sqrt(number1);					
			}
			result = Math.rint(10000000 * result) / 10000000; // Таким образом устанавливается максимальное количество цифр после запятой
			return (isFractNum(result)) ? "" + result : "" + (int) result;// Если число дробное (12.3), то возвращаем его таким, если число целое (12.0), то отсекаем дробную часть.
		}
	}

	public static void main(String[] args) {
		CalcWindow frame = new CalcWindow();
		frame.setTitle("Калькулятор");
		frame.setResizable(false);
		frame.setVisible(true);
	}
}