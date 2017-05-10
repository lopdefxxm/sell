package models;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


public final class GeneralDataType {

	static String modelName = "";
	
	//JdbcUtils jdbcUtils ;
	public static void main(String[] args) {
		show();
	}

	private static String getFilePath() {
		return System.getProperty("user.dir") + "\\src\\models\\"+modelName+".model.xml";
	}

	@SuppressWarnings("unchecked")
	private static Element getNow(Element model, String name) {
		List<Element> list = model.getChildren("DataType");
		for (Element e : list) {
			if (name.equals(e.getAttributeValue("name"))) {
				return e;
			}
		}
		return null;
	}

	public static void doXml(List<DataType> dtList) throws Exception {
		FileInputStream in = new FileInputStream(new File(getFilePath()));
		Document doc = new SAXBuilder().build(in);
		Element model = doc.getRootElement();
		for (DataType dt : dtList) {
			Element child = getNow(model, dt.getName());
			if (child != null) {
				model.removeContent(child);
			}
			Element dataType = new Element("DataType");
			dataType.setAttribute("name", dt.getName());
			if(StringUtils.isNotEmpty(dt.getParent())){
				dataType.setAttribute("parent", dt.getParent());
			}
			Set<PropertyDef> pDefs = dt.getpDefs();
			for (PropertyDef pDef : pDefs) {
				Element propertyDef = new Element("PropertyDef");
				propertyDef.setAttribute("name", pDef.getName());
				Element property = new Element("Property");
				propertyDef.addContent(property);
				property.setAttribute("name", "dataType");
				property.setText(pDef.getType());
				if (StringUtils.isNotEmpty(pDef.getLabel())) {
					property = new Element("Property");
					propertyDef.addContent(property);
					property.setAttribute("name", "label");
					property.setText(pDef.getLabel().trim());
				}
				if (StringUtils.isNotEmpty(pDef.getDefaultValue())) {
					property = new Element("Property");
					propertyDef.addContent(property);
					property.setAttribute("name", "defaultValue");
					property.setText(pDef.getDefaultValue());
				}
				dataType.addContent(propertyDef);
			}
			model.addContent(dataType);
		}
		new XMLOutputter().output(doc, new FileOutputStream(new File(getFilePath())));
	}

	public static void show() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("批量生产Model中的DataType（如果已经存在则会被覆盖掉）");
				frame.setSize(500, 400);
				frame.setLocationRelativeTo(null);

				GroupLayout layout = new GroupLayout(frame.getContentPane());
				frame.getContentPane().setLayout(layout);

				layout.setAutoCreateGaps(true);

				layout.setAutoCreateContainerGaps(true);
				GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

				JLabel labelUrl = new JLabel("URL     ");
				JLabel lableDc = new JLabel("驱动类");
				JLabel lableUser = new JLabel("用户名");
				JLabel lablePwd = new JLabel("密  码");
				JLabel modeLabel = new JLabel("Model");
				JLabel areaLabel = new JLabel("表名称");
				
				final JTextField fieldUrl = new JTextField("jdbc:mysql://localhost:3306/kd");
				final JTextField fieldDc = new JTextField("com.mysql.jdbc.Driver");
				final JTextField fieldUser = new JTextField("root");
				final JTextField fieldPwd = new JTextField("root");
				final JTextField fieldMode = new JTextField();
				/*
				final JTextField fieldUrl = new JTextField("jdbc:oracle:thin:@192.168.168.145:1521:hldrp301");
				final JTextField fieldDc = new JTextField("oracle.jdbc.driver.OracleDriver");
				final JTextField fieldUser = new JTextField("wintest");
				final JTextField fieldPwd = new JTextField("testpound");
				 */
				final JTextArea area = new JTextArea("不同表以','分开");
				area.setLineWrap(true);
				JScrollPane scrollPane = new JScrollPane(area);
				JButton btn = new JButton("生成");
//				JButton btnClear = new JButton("清除");
				btn.addActionListener(new ActionListener() {

					private String getType(String sqlType, int scale) {
						sqlType = sqlType.toUpperCase();
						if ("CHAR".equals(sqlType)) {
							return "Character";
						} 
						if (sqlType.indexOf("CHAR") != -1) {
							return "String";
						}
						if("BIGINT".equals(sqlType)){
							return "Long";
						}
						//以INT开头的或者INT结尾
						if (sqlType.endsWith("INT")|| sqlType.startsWith("INT")) {
							return "Integer";
						}else if ("DATE".equals(sqlType)) {
							return "Date";
						}else if ("DATETIME".equals(sqlType)) {
							return "DateTime";
						}else if (sqlType.equals("DOUBLE")) {
							return "Double";
						}
						return "String";
					}

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Connection con = null;
						Statement st = null;
						try {
							modelName = fieldMode.getText();
							if(StringUtils.isEmpty(modelName)){
								throw new Exception("请填写对应的Model！");
							}
							Class.forName(fieldDc.getText());
							con = DriverManager.getConnection(fieldUrl.getText(), fieldUser.getText(), fieldPwd.getText());
							st = con.createStatement();
							String[] tables = area.getText().split(",");
							List<DataType> dtList = new ArrayList<DataType>();
							for (String table : tables) {
								DataType dt = new GeneralDataType().new DataType();
								dt.setName(table.toUpperCase());
								String sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS "
								+ " WHERE TABLE_NAME = '"+table.toUpperCase()+"' AND TABLE_SCHEMA = 'kd' ORDER BY ORDINAL_POSITION";

								ResultSet rs = st.executeQuery(sql);
								while (rs.next()) {
									String columnName = rs.getString("COLUMN_NAME");
									PropertyDef pDef = new GeneralDataType().new PropertyDef();
									pDef.setName(columnName);
									pDef.setLabel(rs.getString("COLUMN_COMMENT"));
									int scale = rs.getInt("CHARACTER_MAXIMUM_LENGTH");
									String type = this.getType(rs.getString("DATA_TYPE"), scale);
									pDef.setType(type);
									
									if ("UPDATE_DATE".equals(columnName) || "UPDATE_USER".equals(columnName)
											|| "CREATE_DATE".equals(columnName) || "CREATE_USER".equals(columnName) 
											|| "ACTIVE".equals(columnName) || "MEMO".equals(columnName) ) {
										dt.setParent("BASE_TYPE");
										continue;
									} 
									dt.addPDef(pDef);
								}
								dtList.add(dt);
							}
							GeneralDataType.doXml(dtList);
							JOptionPane.showMessageDialog(null, "已经成功生产所需要的DataType！请关闭model文件，然后刷新，再打开即可。");
							//System.exit(0);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
						} finally {
							if (con != null) {
								try {
									con.close();
								} catch (SQLException e) {
									throw new RuntimeException(e);
								}
							}
						}
					}

				});
				hGroup.addGroup(layout.createParallelGroup().addComponent(labelUrl).addComponent(lableDc).addComponent(lableUser).addComponent(lablePwd).addComponent(modeLabel)
						.addComponent(areaLabel));
				hGroup.addGroup(layout.createParallelGroup().addComponent(fieldUrl).addComponent(fieldDc).addComponent(fieldUser).addComponent(fieldPwd).addComponent(fieldMode)
						.addComponent(scrollPane).addComponent(btn, Alignment.CENTER));
				layout.setHorizontalGroup(hGroup);

				GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(labelUrl).addComponent(fieldUrl));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lableDc).addComponent(fieldDc));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lableUser).addComponent(fieldUser));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lablePwd).addComponent(fieldPwd));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(modeLabel).addComponent(fieldMode));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(areaLabel).addComponent(scrollPane));
				vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(btn, Alignment.CENTER));
				layout.setVerticalGroup(vGroup);

				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}

		});
	}

	class DataType {
		private String name;
		private String parent;
		private Set<PropertyDef> pDefs = new LinkedHashSet<PropertyDef>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getParent() {
			return parent;
		}

		public void setParent(String parent){
			this.parent=parent;
		}
		public Set<PropertyDef> getpDefs() {
			return pDefs;
		}

		public void addPDef(PropertyDef pDef) {
			this.pDefs.add(pDef);
		}

	}

	class PropertyDef {
		private String name;
		private String type;

		private String defaultValue;
		private String label;
		private String readOnly;
		private String required;
		private List<Validator> vList = new ArrayList<Validator>();

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getReadOnly() {
			return readOnly;
		}

		public void setReadOnly(String readOnly) {
			this.readOnly = readOnly;
		}

		public String getRequired() {
			return required;
		}

		public void setRequired(String required) {
			this.required = required;
		}

		public List<Validator> getvList() {
			return vList;
		}

		public void addValidator(Validator v) {
			this.vList.add(v);
		}

	}

	class Validator {
		private final String runAt = "both";
		private String type;
		private String maxValue;
		private String minValue;
		private String maxLength;
		private String minLength;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(String maxValue) {
			this.maxValue = maxValue;
		}

		public String getMinValue() {
			return minValue;
		}

		public void setMinValue(String minValue) {
			this.minValue = minValue;
		}

		public String getMaxLength() {
			return maxLength;
		}

		public void setMaxLength(String maxLength) {
			this.maxLength = maxLength;
		}

		public String getMinLength() {
			return minLength;
		}

		public void setMinLength(String minLength) {
			this.minLength = minLength;
		}

		public String getRunAt() {
			return runAt;
		}

	}
}
