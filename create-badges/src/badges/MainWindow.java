package badges;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.DocumentException;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -2528656980938482095L;
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
		setBounds(100, 100, 450, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel backTemplatePanel = new JPanel();
		backTemplatePanel.setBounds(6, 6, 206, 42);
		contentPane.add(backTemplatePanel);
		GridBagLayout gbl_backTemplatePanel = new GridBagLayout();
		gbl_backTemplatePanel.columnWidths = new int[] {150, 40, 0};
		gbl_backTemplatePanel.rowHeights = new int[] {14, 20, 0};
		gbl_backTemplatePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_backTemplatePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		backTemplatePanel.setLayout(gbl_backTemplatePanel);
		
		JLabel lblBackgroundTemplatepdf = new JLabel("Background Template (pdf):");
		GridBagConstraints gbc_lblBackgroundTemplatepdf = new GridBagConstraints();
		gbc_lblBackgroundTemplatepdf.gridwidth = 2;
		gbc_lblBackgroundTemplatepdf.fill = GridBagConstraints.BOTH;
		gbc_lblBackgroundTemplatepdf.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackgroundTemplatepdf.gridx = 0;
		gbc_lblBackgroundTemplatepdf.gridy = 0;
		backTemplatePanel.add(lblBackgroundTemplatepdf, gbc_lblBackgroundTemplatepdf);
		
		JButton btnOpenBackTemplatePath = new JButton("...");
		btnOpenBackTemplatePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = openFileDialog("Choose Back Template Pdf...", "pdf", "Pdf Files");
				if (f != null) {
					backTemplatePath.setText(f.getAbsolutePath());
				}
			}
		});
		
		backTemplatePath = new JTextField();
		GridBagConstraints gbc_backTemplatePath = new GridBagConstraints();
		gbc_backTemplatePath.fill = GridBagConstraints.BOTH;
		gbc_backTemplatePath.insets = new Insets(0, 0, 0, 5);
		gbc_backTemplatePath.gridx = 0;
		gbc_backTemplatePath.gridy = 1;
		backTemplatePanel.add(backTemplatePath, gbc_backTemplatePath);
		backTemplatePath.setColumns(15);
		btnOpenBackTemplatePath.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_btnOpenBackTemplatePath = new GridBagConstraints();
		gbc_btnOpenBackTemplatePath.fill = GridBagConstraints.BOTH;
		gbc_btnOpenBackTemplatePath.gridx = 1;
		gbc_btnOpenBackTemplatePath.gridy = 1;
		backTemplatePanel.add(btnOpenBackTemplatePath, gbc_btnOpenBackTemplatePath);
		
		JPanel frontTemplatePanel = new JPanel();
		frontTemplatePanel.setBounds(6, 59, 206, 42);
		contentPane.add(frontTemplatePanel);
		GridBagLayout gbl_frontTemplatePanel = new GridBagLayout();
		gbl_frontTemplatePanel.columnWidths = new int[] {150, 40, 0};
		gbl_frontTemplatePanel.rowHeights = new int[]{14, 20, 0};
		gbl_frontTemplatePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_frontTemplatePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frontTemplatePanel.setLayout(gbl_frontTemplatePanel);
		
		JButton btnOpenFrontTemplatePath = new JButton("...");
		btnOpenFrontTemplatePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = openFileDialog("Choose Front Template Pdf...", "pdf", "Pdf Files");
				if (f != null) {
					frontTemplatePath.setText(f.getAbsolutePath());
				}
			}
		});
		
		JLabel lblFrontsideTemplatepdf = new JLabel("Frontside Template (pdf):");
		GridBagConstraints gbc_lblFrontsideTemplatepdf = new GridBagConstraints();
		gbc_lblFrontsideTemplatepdf.gridwidth = 2;
		gbc_lblFrontsideTemplatepdf.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFrontsideTemplatepdf.anchor = GridBagConstraints.NORTH;
		gbc_lblFrontsideTemplatepdf.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrontsideTemplatepdf.gridx = 0;
		gbc_lblFrontsideTemplatepdf.gridy = 0;
		frontTemplatePanel.add(lblFrontsideTemplatepdf, gbc_lblFrontsideTemplatepdf);
		
		frontTemplatePath = new JTextField();
		frontTemplatePath.setColumns(15);
		GridBagConstraints gbc_frontTemplatePath = new GridBagConstraints();
		gbc_frontTemplatePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_frontTemplatePath.anchor = GridBagConstraints.NORTH;
		gbc_frontTemplatePath.insets = new Insets(0, 0, 0, 5);
		gbc_frontTemplatePath.gridx = 0;
		gbc_frontTemplatePath.gridy = 1;
		frontTemplatePanel.add(frontTemplatePath, gbc_frontTemplatePath);
		btnOpenFrontTemplatePath.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_btnOpenFrontTemplatePath = new GridBagConstraints();
		gbc_btnOpenFrontTemplatePath.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOpenFrontTemplatePath.gridx = 1;
		gbc_btnOpenFrontTemplatePath.gridy = 1;
		frontTemplatePanel.add(btnOpenFrontTemplatePath, gbc_btnOpenFrontTemplatePath);
		
		JPanel textInputPanel = new JPanel();
		textInputPanel.setBounds(6, 112, 206, 42);
		contentPane.add(textInputPanel);
		GridBagLayout gbl_textInputPanel = new GridBagLayout();
		gbl_textInputPanel.columnWidths = new int[] {150, 40, 0};
		gbl_textInputPanel.rowHeights = new int[]{14, 20, 0};
		gbl_textInputPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_textInputPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		textInputPanel.setLayout(gbl_textInputPanel);
		
		JButton btnOpenInputPath = new JButton("...");
		btnOpenInputPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = openFileDialog("Choose Text Input file...", "txt", "Text Files");
				if (f != null) {
					textInputPath.setText(f.getAbsolutePath());
				}
			}
		});
		
		JLabel lblTextInput = new JLabel("Text Input Path:");
		GridBagConstraints gbc_lblTextInput = new GridBagConstraints();
		gbc_lblTextInput.gridwidth = 2;
		gbc_lblTextInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTextInput.anchor = GridBagConstraints.NORTH;
		gbc_lblTextInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblTextInput.gridx = 0;
		gbc_lblTextInput.gridy = 0;
		textInputPanel.add(lblTextInput, gbc_lblTextInput);
		
		textInputPath = new JTextField();
		textInputPath.setColumns(15);
		GridBagConstraints gbc_textInputPath = new GridBagConstraints();
		gbc_textInputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textInputPath.anchor = GridBagConstraints.NORTH;
		gbc_textInputPath.insets = new Insets(0, 0, 0, 5);
		gbc_textInputPath.gridx = 0;
		gbc_textInputPath.gridy = 1;
		textInputPanel.add(textInputPath, gbc_textInputPath);
		btnOpenInputPath.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_btnOpenInputPath = new GridBagConstraints();
		gbc_btnOpenInputPath.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOpenInputPath.gridx = 1;
		gbc_btnOpenInputPath.gridy = 1;
		textInputPanel.add(btnOpenInputPath, gbc_btnOpenInputPath);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBounds(6, 165, 206, 67);
		contentPane.add(outputPanel);
		GridBagLayout gbl_outputPanel = new GridBagLayout();
		gbl_outputPanel.columnWidths = new int[] {150, 40, 0};
		gbl_outputPanel.rowHeights = new int[]{14, 20, 23, 0};
		gbl_outputPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_outputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		outputPanel.setLayout(gbl_outputPanel);
		
		JLabel lblOutputPath = new JLabel("Badges Output Path:");
		GridBagConstraints gbc_lblOutputPath = new GridBagConstraints();
		gbc_lblOutputPath.gridwidth = 2;
		gbc_lblOutputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOutputPath.anchor = GridBagConstraints.NORTH;
		gbc_lblOutputPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputPath.gridx = 0;
		gbc_lblOutputPath.gridy = 0;
		outputPanel.add(lblOutputPath, gbc_lblOutputPath);
		
		outputPath = new JTextField();
		outputPath.setColumns(15);
		GridBagConstraints gbc_outputPath = new GridBagConstraints();
		gbc_outputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputPath.anchor = GridBagConstraints.NORTH;
		gbc_outputPath.insets = new Insets(0, 0, 5, 5);
		gbc_outputPath.gridx = 0;
		gbc_outputPath.gridy = 1;
		outputPanel.add(outputPath, gbc_outputPath);
		
		JButton btnOpenOutputPath = new JButton("...");
		btnOpenOutputPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = openFileDialog("Choose Pdf Output Path...", "pdf", "Pdf Files");
				if (f != null) {
					outputPath.setText(f.getAbsolutePath());
				}
			}
		});
		btnOpenOutputPath.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_btnOpenOutputPath = new GridBagConstraints();
		gbc_btnOpenOutputPath.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOpenOutputPath.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenOutputPath.gridx = 1;
		gbc_btnOpenOutputPath.gridy = 1;
		outputPanel.add(btnOpenOutputPath, gbc_btnOpenOutputPath);
		
		final JCheckBox chckbxMergeOutput = new JCheckBox("Merge Badges onto A4");
		GridBagConstraints gbc_chckbxMergeOutput = new GridBagConstraints();
		gbc_chckbxMergeOutput.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxMergeOutput.gridwidth = 2;
		gbc_chckbxMergeOutput.gridx = 0;
		gbc_chckbxMergeOutput.gridy = 2;
		outputPanel.add(chckbxMergeOutput, gbc_chckbxMergeOutput);
		
		JButton btnGenerateBadges = new JButton("Generate Badges");
		btnGenerateBadges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] generatorArgs = { 
						textInputPath.getText(),
						frontTemplatePath.getText(),
						backTemplatePath.getText(),
						outputPath.getText()
					};
				GenerateBadges.main(generatorArgs);
				
				
				try {
					if (chckbxMergeOutput.isSelected()) {
						File bak = new File(outputPath.getText() + ".bak");
						File src = new File(outputPath.getText());
						src.renameTo(bak);
						File dst = new File(outputPath.getText());
						MergeBadges.manipulatePdf(bak.getAbsolutePath(), dst.getAbsolutePath(), 2);
						JOptionPane.showMessageDialog(getContentPane(), "Done & Merged.");
						bak.delete();
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Done.");
					}
				} catch (IOException | DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnGenerateBadges.setBounds(6, 243, 194, 29);
		contentPane.add(btnGenerateBadges);
		
		JPanel previewPanel = new JPanel();
		previewPanel.setBounds(224, 6, 200, 266);
		contentPane.add(previewPanel);
	}

	private String basePath = System.getProperty("user.dir");
	private File openFileDialog(String title, String fileType, String fileTypeCaption) {
		JFileChooser fileopen = new JFileChooser(basePath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileTypeCaption, fileType);
		fileopen.addChoosableFileFilter(filter);

		int ret = fileopen.showDialog(null, title);
		if (ret == JFileChooser.APPROVE_OPTION) {
		  File f = fileopen.getSelectedFile();
		  basePath = f.getPath();
		  return f;
		}
		
		return null;
	}
}
