package badges;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import com.itextpdf.text.DocumentException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField backTemplatePath;
	private JTextField frontTemplatePath;
	private JTextField textInputPath;
	private JTextField outputPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("noprobLAN Badges");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel backTemplatePanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) backTemplatePanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		backTemplatePanel.setBounds(6, 6, 206, 58);
		contentPane.add(backTemplatePanel);
		
		JLabel lblBackgroundTemplatepdf = new JLabel("Background Template (pdf):");
		backTemplatePanel.add(lblBackgroundTemplatepdf);
		
		backTemplatePath = new JTextField();
		backTemplatePanel.add(backTemplatePath);
		backTemplatePath.setColumns(15);
		
		JPanel frontTemplatePanel = new JPanel();
		FlowLayout fl_frontTemplatePanel = (FlowLayout) frontTemplatePanel.getLayout();
		fl_frontTemplatePanel.setAlignment(FlowLayout.LEFT);
		frontTemplatePanel.setBounds(6, 62, 206, 58);
		contentPane.add(frontTemplatePanel);
		
		JLabel lblFrontsideTemplatepdf = new JLabel("Frontside Template (pdf):");
		frontTemplatePanel.add(lblFrontsideTemplatepdf);
		
		frontTemplatePath = new JTextField();
		frontTemplatePath.setColumns(15);
		frontTemplatePanel.add(frontTemplatePath);
		
		JPanel textInputPanel = new JPanel();
		FlowLayout fl_textInputPanel = (FlowLayout) textInputPanel.getLayout();
		fl_textInputPanel.setAlignment(FlowLayout.LEFT);
		textInputPanel.setBounds(6, 119, 206, 58);
		contentPane.add(textInputPanel);
		
		JLabel lblTextInput = new JLabel("Text Input:");
		textInputPanel.add(lblTextInput);
		
		textInputPath = new JTextField();
		textInputPath.setColumns(15);
		textInputPanel.add(textInputPath);
		
		JPanel outputPanel = new JPanel();
		FlowLayout fl_outputPanel = (FlowLayout) outputPanel.getLayout();
		fl_outputPanel.setAlignment(FlowLayout.LEFT);
		outputPanel.setBounds(6, 176, 206, 88);
		contentPane.add(outputPanel);
		
		JLabel lblOutputPath = new JLabel("Output Path:");
		outputPanel.add(lblOutputPath);
		
		outputPath = new JTextField();
		outputPath.setColumns(15);
		outputPanel.add(outputPath);
		
		JCheckBox chckbxMergeOutput = new JCheckBox("Merge Badges onto A4");
		outputPanel.add(chckbxMergeOutput);
		
		JButton btnGenerateBadges = new JButton("Generate Badges");
		btnGenerateBadges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] generatorArgs = { 
					textInputPath.getText(),
					frontTemplatePath.getText(),
					backTemplatePath.getText(),
					outputPath.getText()
				};
				GenerateBadges.main(generatorArgs);
				
				try {
					MergeBadges.manipulatePdf(outputPath.getText(), outputPath.getText(), 2);
				} catch (IOException | DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerateBadges.setBounds(6, 263, 206, 29);
		contentPane.add(btnGenerateBadges);
		
		JPanel previewPanel = new JPanel();
		previewPanel.setBounds(224, 6, 220, 286);
		contentPane.add(previewPanel);
	}
}
