package dpo2_u2_a3_arcm;

//importo las bibliotecas requeridas
import java.awt.*;
import java.awt.event.*;
import java.sql.*;                                  //permite conectar con bd
import java.text.MessageFormat;                     //permite nombrar paginas de impresion
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;     //permite cerrar la aplicacion
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ari Chavez
 */
public class Ventana extends JFrame  {
    //creo los componentes y contenedores   
    JPanel panel = new JPanel(); 
    
    JLabel tag = new JLabel();          //Mensaje de bienvenida
    JLabel tag2 = new JLabel();
    JLabel end = new JLabel();          //para imagen de fondo  
    JLabel fondo = new JLabel();         //para imagen de fondo  
  
    JButton save = new JButton();       //creación de botones
    JButton clean = new JButton();
    JButton see = new JButton();
    JComboBox select = new JComboBox();
    JButton exit = new JButton();
    
    //creación de botonesInventario
    JButton capturarInv = new JButton();          
    JButton cleanInv = new JButton();
    JButton seeInv = new JButton();
    JComboBox selectInv = new JComboBox();
    JButton exitInv = new JButton();
    
    //botones submenu personal
    JButton capturarPers = new JButton();          
    JButton cleanPers = new JButton();
    JButton seePers = new JButton();
    JComboBox selectPers = new JComboBox();
    JButton exitPers = new JButton();
    
    //botones submenu reporte
    JButton print = new JButton();  
    JButton exitReport = new JButton();
    JComboBox selectSuc= new JComboBox();
    JLabel repoSuc = new JLabel();
    JButton repo = new JButton();  
    DefaultTableModel tableRepo = new DefaultTableModel();
    
    JMenuBar barra = new JMenuBar();        //creacion de barra de menu
    
    public Ventana() {                  //constructor
        panel();                        //llamo mi panel
        complementos();                 //llamo mis complementos
        menu();
    }

    //configuracion ventana
    private void panel() {   
        this.setSize(800, 800);                                         //tamaño
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);                   
        this.setTitle("Restarante de Comida Mexicana. Hecho por: Ari Chavez");
        this.setLocationRelativeTo(null);   
    }
    
    //creación de menu
     public void menu(){
        JMenu ventas = new JMenu();
        JMenu mInv = new JMenu();
        JMenu personal = new JMenu();
        JMenu mSalir = new JMenu();
        JMenu reporte = new JMenu();
        
        //creacion de submenus
        JMenuItem v1 = new JMenuItem();
        JMenuItem mInv1 = new JMenuItem();
        JMenuItem pers1 = new JMenuItem();      
        JMenuItem ver = new JMenuItem();
        
        //Agrego evento actionListener para el submenu Ventas     
        ActionListener v1Click = new ActionListener (){                       
                @Override
                 public void actionPerformed(ActionEvent ae) {               
                    exit.setVisible(true);          //hago visibles los botones
                    save.setVisible(true);
                    clean.setVisible(true);
                    see.setVisible(true);
                    select.setVisible(true);
                    tablventas();                   //mi tabla de datos
                    barra.setVisible(false);
                    fondo.setVisible(false);
                    end.setVisible(false);

                }
            };      
        v1.addActionListener(v1Click);
        
        //Agrego evento actionListener para el submenu inventario     
            ActionListener mInvClick = new ActionListener (){                       
                @Override
                 public void actionPerformed(ActionEvent ae) {
                    exitInv.setVisible(true);
                    capturarInv.setVisible(true);
                    cleanInv.setVisible(true);
                    seeInv.setVisible(true);
                    selectInv.setVisible(true);
                    inventarios();
                    barra.setVisible(false);
                    end.setVisible(false);
                    fondo.setVisible(false);
                
                }
            };      
        mInv1.addActionListener(mInvClick);
        
        //Agrego evento actionListener para el submenu personal     
            ActionListener pers1Click = new ActionListener (){                       
                @Override
                 public void actionPerformed(ActionEvent ae) {
                    exitPers.setVisible(true);
                    capturarPers.setVisible(true);       //cambiar bototnes
                    cleanPers.setVisible(true);
                    seePers.setVisible(true);
                    selectPers.setVisible(true);
                    personal();
                    barra.setVisible(false);
                    end.setVisible(false);
                    fondo.setVisible(false);
                }
            };      
        pers1.addActionListener(pers1Click);
        
        //Agrego evento actionListener para el menu salir     
            ActionListener subSalir = new ActionListener (){                       
                @Override
                 public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                 }
            };      
        mSalir.addActionListener(subSalir);
        
         //Agrego evento actionListener para el submenu reporte     
            ActionListener verReport = new ActionListener (){                       
                @Override
                 public void actionPerformed(ActionEvent ae) {
                    print.setVisible(true);
                    selectSuc.setVisible(true);
                    exitReport.setVisible(true);
                    barra.setVisible(false);
                    end.setVisible(false);
                    fondo.setVisible(false);
                    reporte();
                    repo.setVisible(true);
                    repoCombo();
                }
            };      
        ver.addActionListener(verReport);
       
        //nombro el menu
        ventas.setText("Ventas");
        mInv.setText("Inventario");
        personal.setText("Personal");
        reporte.setText("Reporte");
        mSalir.setText("Salir");

        //agrego los botones del menu y submenu a la barra
        barra.add(ventas);
        barra.add(mInv);
        barra.add(personal);
        barra.add(reporte); 
        barra.add(mSalir);
        //barra.add(mInv1); WTF?
        //barra.add(pers1);
        
        //configuro la barra
        barra.setBounds(0,0, 800, 50);
        panel.add(barra);
        barra.setOpaque(false);
        barra.setVisible(true);
        barra.doLayout();
        
        //equiqueto los submenus
        v1.setText("Tabla");
        mInv1.setText("Tabla");
        pers1.setText("Tabla");
        ver.setText("Ver");
        
        //agrego submenus    
        ventas.add(v1);  
        mInv.add(mInv1);
        personal.add(pers1);
        reporte.add(ver);
    }
                
    public void tablventas(){  
        //datos tabla del personal
        Object[] nombreCol = {"Artículo", "Precio", "Marca", "Cantidad", "Sucursal"};
        Object[] filas = new Object[5]; 
        
        //creo modelo de tabla  
        DefaultTableModel dtm = new DefaultTableModel();
                
        //creo tabla
        dtm.setColumnIdentifiers(nombreCol);                //establesco columnas
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table); 
        table.setPreferredScrollableViewportSize(new Dimension(100,100));
        getContentPane().add(scrollPane, BorderLayout.NORTH);
        table.setModel(dtm);
        
        //formulario etiquetas
        JLabel art = new JLabel("Articulo");
        JLabel cost = new JLabel("Precio");
        JLabel tm = new JLabel("Marca");
        JLabel cant = new JLabel("Cantidad");
        JLabel suc = new JLabel("Sucursal");

        //agrego las etiquetas
        panel.add(art);
        panel.add(cost);
        panel.add(tm);
        panel.add(cant);
        panel.add(suc);
                
        art.setBounds(10, 75, 100, 100);
        art.setOpaque(false);
        art.setVisible(true);
        art.setFont(new Font("arial",Font.BOLD,14));
        JTextField artTxt = new JTextField();
        panel.add(artTxt);
        artTxt.setBounds(80, 115, 300, 20);
        
        cant.setBounds(10, 50, 100, 100);
        cant.setOpaque(false);
        cant.setVisible(true);
        cant.setFont(new Font("arial",Font.BOLD,14));
        JTextField cantTxt = new JTextField();
        panel.add(cantTxt);
        cantTxt.setBounds(80, 90, 300, 20);
               
        cost.setBounds(10, 100, 100, 100);
        cost.setOpaque(false);
        cost.setVisible(true);
        cost.setFont(new Font("arial",Font.BOLD,14));
        JTextField costTxt = new JTextField();
        panel.add(costTxt);
        costTxt.setBounds(80, 140, 300, 20);
        
        tm.setBounds(10, 125, 100, 100);
        tm.setOpaque(false);
        tm.setVisible(true);
        tm.setFont(new Font("arial",Font.BOLD,14));
        JTextField tmTxt = new JTextField();
        panel.add(tmTxt);
        tmTxt.setBounds(80, 165, 300, 20);
        
        suc.setBounds(10, 150, 100, 100);
        suc.setOpaque(false);
        suc.setVisible(true);
        suc.setFont(new Font("arial",Font.BOLD,14));
        JTextField sucTxt = new JTextField();
        panel.add(sucTxt);
        sucTxt.setBounds(80, 190, 300, 20);
        
         //Agrego evento actionListener boton guardarVentas para conexion bd
            ActionListener cap = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                    Conexion con = new Conexion();          //objeto clase Conexion
                    Connection cn = con.getConnection();    //debe guardar datos en bd
                    String sql= "";
                    sql = "INSERT INTO restaurantebd.ventas (articulo, precio,"
                            + "marca, cantidad, sucursal) VALUES (?,?,?,?,?)";     //para guardar en columnas en la tabla precreada en el SGBD
                    try {
                        //Envio statements
                        PreparedStatement psd = cn.prepareStatement(sql);
                        psd.setObject(1, filas[0]);
                        psd.setObject(2, filas[1]);
                        psd.setObject(3, filas[2]);
                        psd.setObject(4, filas[3]);
                        psd.setObject(5, filas[4]);
                        
                        //mensaje de guardado exitoso
                        int n = psd.executeUpdate();
                        if (n>0) {
                            JOptionPane.showMessageDialog(null, "Guardado");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }};
        save.addActionListener(cap);
        
        //Agrego evento actionListener boton seleccionar
            ActionListener edit = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                select.setEditable(true);
            }};
        select.addActionListener(edit);
        
        //Agrego evento actionListener boton visualizar
            ActionListener ver = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {               
                //muestro lo escrito en los textifields al Jtable
                filas[0]=artTxt.getText();
                filas[1]=costTxt.getText();
                filas[2]=tmTxt.getText();
                filas[3]=cantTxt.getText();   
                filas[4]=sucTxt.getText();
                dtm.addRow(filas);                  //añade fila de datos cada vez que hago click en boton
                select.addItem(filas[0].toString());//añade los registros al combobox seleccionar   
            }};
        see.addActionListener(ver);
        
        //Agrego evento actionListener boton limpiar
            ActionListener clear = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
              sucTxt.setText(null);
              cantTxt.setText(null);
              artTxt.setText(null);
              costTxt.setText(null);
              tmTxt.setText(null);
            }};
        clean.addActionListener(clear);
        
        //Agrego evento actionListener boton regresar
            ActionListener bBack = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                complementos();
                save.setVisible(false);          
                clean.setVisible(false);
                see.setVisible(false);
                exit.setVisible(false);
                select.setVisible(false);
                sucTxt.setVisible(false);
                cantTxt.setVisible(false);
                artTxt.setVisible(false);
                tmTxt.setVisible(false);
                costTxt.setVisible(false);
                art.setVisible(false);
                cost.setVisible(false);
                tm.setVisible(false);
                suc.setVisible(false);
                cant.setVisible(false);
                scrollPane.setVisible(false);
                barra.setVisible(true);
            }};
        exit.addActionListener(bBack); 
    }
        
    public void inventarios(){  
        //datos tabla del inventario
        Object[] nombreCol2 = {"Clave del Producto", "Sucursal", "Producto", "Cantidad", "Marca", "Proveedor"}; 
        Object[] filasInv = new Object[6];
              
        DefaultTableModel dtm2 = new DefaultTableModel();
        
         //creo tabla
        dtm2.setColumnIdentifiers(nombreCol2);                //establesco columnas
        JTable tableInv = new JTable(dtm2);
        JScrollPane scrollPaneInv = new JScrollPane(tableInv); 
        tableInv.setPreferredScrollableViewportSize(new Dimension(100,100));
        getContentPane().add(scrollPaneInv, BorderLayout.NORTH);
        tableInv.setModel(dtm2);
        
        //formulario etiquetas
        JLabel clave = new JLabel("Clave");
        JLabel suc = new JLabel("Sucursal");
        JLabel prod = new JLabel("Producto");
        JLabel cant = new JLabel("Cantidad");
        JLabel marca = new JLabel("Marca");
        JLabel provee = new JLabel("Proveedor");

        //agrego las etiquetas
        panel.add(clave);
        panel.add(suc);
        panel.add(prod);
        panel.add(cant);
        panel.add(marca);
        panel.add(provee);  
                
        //configuro etiquetas y textfields
        clave.setBounds(10, 0, 100, 100);
        clave.setOpaque(false);
        clave.setVisible(true);
        clave.setFont(new Font("arial",Font.BOLD,14));
        JTextField claveTxt = new JTextField();
        panel.add(claveTxt);
        claveTxt.setBounds(100, 40, 300, 20);
     
        suc.setBounds(10, 25, 100, 100);
        suc.setOpaque(false);
        suc.setVisible(true);
        suc.setFont(new Font("arial",Font.BOLD,14));
        JTextField sucTxt = new JTextField();
        panel.add(sucTxt);
        sucTxt.setBounds(100, 65, 300, 20);
        
        prod.setBounds(10, 50, 100, 100);
        prod.setOpaque(false);
        prod.setVisible(true);
        prod.setFont(new Font("arial",Font.BOLD,14));
        JTextField prodTxt = new JTextField();
        panel.add(prodTxt);
        prodTxt.setBounds(100, 90, 300, 20);
       
        cant.setBounds(10, 75, 100, 100);
        cant.setOpaque(false);
        cant.setVisible(true);
        cant.setFont(new Font("arial",Font.BOLD,14));
        JTextField cantTxt = new JTextField();
        panel.add(cantTxt);
        cantTxt.setBounds(100, 115, 300, 20);
        
        marca.setBounds(10, 100, 100, 100);
        marca.setOpaque(false);
        marca.setVisible(true);
        marca.setFont(new Font("arial",Font.BOLD,14));
        JTextField marcaTxt = new JTextField();
        panel.add(marcaTxt);
        marcaTxt.setBounds(100, 140, 300, 20);
        
        provee.setBounds(10, 125, 100, 100);
        provee.setOpaque(false);
        provee.setVisible(true);
        provee.setFont(new Font("arial",Font.BOLD,14));
        JTextField proveeTxt = new JTextField();
        panel.add(proveeTxt);
        proveeTxt.setBounds(100, 165, 300, 20);
        
        //Agrego evento actionListener boton seleccionarInv
            ActionListener editInv = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                selectInv.setEditable(true);
            }};
        selectInv.addActionListener(editInv);
        
        //Agrego evento actionListener boton visualizar
            ActionListener ver = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {               
                //muestro lo escrito en los textifields al Jtable
                filasInv[0]=claveTxt.getText();
                filasInv[1]=sucTxt.getText();
                filasInv[2]=prodTxt.getText();
                filasInv[3]=cantTxt.getText();
                filasInv[4]=marcaTxt.getText();
                filasInv[5]=proveeTxt.getText();                
                dtm2.addRow(filasInv);                    //añade fila de datos cada vez que hago click en boton
                selectInv.addItem(filasInv[0].toString());//añade los registros al combobox seleccionar   
            }};
        seeInv.addActionListener(ver);
        
        //Agrego evento actionListener boton limpiar
            ActionListener clear = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
              claveTxt.setText(null);
              sucTxt.setText(null);
              prodTxt.setText(null);
              cantTxt.setText(null);
              marcaTxt.setText(null);
              proveeTxt.setText(null);
            }};
        cleanInv.addActionListener(clear);
        
        //Agrego evento actionListener boton capturar para conexion bd
            ActionListener cap = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                    Conexion con = new Conexion();          //objeto clase Conexion
                    Connection cn = con.getConnection();    //debe guardar datos en bd
                    String sql= "";
                    sql = "INSERT INTO restaurantebd.inventarios (ClaveDelProducto, Sucursal,"
                            + "Producto, Cantidad, Marca, Proveedor) VALUES (?,?,?,?,?,?)";     //para guardar en columnas en la tabla precreada en el SGBD
                    try {
                        //Envio statements
                        PreparedStatement psd = cn.prepareStatement(sql);
                        psd.setObject(1, filasInv[0]);
                        psd.setObject(2, filasInv[1]);
                        psd.setObject(3, filasInv[2]);
                        psd.setObject(4, filasInv[3]);
                        psd.setObject(5, filasInv[4]);
                        psd.setObject(6, filasInv[5]);
                        
                        //mensaje de guardado exitoso
                        int n = psd.executeUpdate();
                        if (n>0) {
                            JOptionPane.showMessageDialog(null, "Guardado");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }};
        capturarInv.addActionListener(cap);
        
        //Agrego evento actionListener boton regresar
            ActionListener bBackInv = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                complementos();
                capturarInv.setVisible(false);          
                cleanInv.setVisible(false);
                seeInv.setVisible(false);
                exitInv.setVisible(false);
                selectInv.setVisible(false);
                claveTxt.setVisible(false);
                sucTxt.setVisible(false);
                cantTxt.setVisible(false);
                prodTxt.setVisible(false);
                cantTxt.setVisible(false);
                marcaTxt.setVisible(false);
                proveeTxt.setVisible(false);
                clave.setVisible(false);
                suc.setVisible(false);
                prod.setVisible(false);
                cant.setVisible(false);
                marca.setVisible(false);
                provee.setVisible(false);
                cant.setVisible(false);
                scrollPaneInv.setVisible(false);
                barra.setVisible(true);
            }};
        exitInv.addActionListener(bBackInv); 
    }   
     
        public void personal(){  
        //datos tabla del personal
        Object[] nombreCol3 = {"Número de Empleado", "Sucursal", "Nombre", "Apellido", "Edad", "Sueldo"};         
        Object[] filasPers = new Object[6];
        
        DefaultTableModel dtm3 = new DefaultTableModel();
        
        //creo tablas
        dtm3.setColumnIdentifiers(nombreCol3);
        JTable tablePers = new JTable(dtm3);
        
        //creo contenedor de tabla
        JScrollPane scrollPanePers = new JScrollPane(tablePers);
        tablePers.setPreferredScrollableViewportSize(new Dimension(100,100));
        getContentPane().add(scrollPanePers, BorderLayout.NORTH);
        tablePers.setModel(dtm3);
        
        //formulario etiquetas
        JLabel nEmpl = new JLabel("Número");
        JLabel sucPers = new JLabel("Sucursal");
        JLabel nombreEmpl = new JLabel("Nombre");
        JLabel apellidoEmpl = new JLabel("Apellido");
        JLabel edadEmpl = new JLabel("Edad");
        JLabel sueldoEmpl = new JLabel("Sueldo");

        //agrego las etiquetas
        panel.add(nEmpl);
        panel.add(sucPers);
        panel.add(nombreEmpl);
        panel.add(apellidoEmpl);
        panel.add(edadEmpl);
        panel.add(sueldoEmpl);  
        
        //configuro etiquetas y textfields
        nEmpl.setBounds(10, 0, 100, 100);
        nEmpl.setOpaque(false);
        nEmpl.setVisible(true);
        nEmpl.setFont(new Font("arial",Font.BOLD,14));
        JTextField nEmplTxt = new JTextField();
        panel.add(nEmplTxt);
        nEmplTxt.setBounds(100, 40, 300, 20);
     
        sucPers.setBounds(10, 25, 100, 100);
        sucPers.setOpaque(false);
        sucPers.setVisible(true);
        sucPers.setFont(new Font("arial",Font.BOLD,14));
        JTextField sucPersTxt = new JTextField();
        panel.add(sucPersTxt);
        sucPersTxt.setBounds(100, 65, 300, 20);
        
        nombreEmpl.setBounds(10, 50, 100, 100);
        nombreEmpl.setOpaque(false);
        nombreEmpl.setVisible(true);
        nombreEmpl.setFont(new Font("arial",Font.BOLD,14));
        JTextField nombreEmplTxt = new JTextField();
        panel.add(nombreEmplTxt);
        nombreEmplTxt.setBounds(100, 90, 300, 20);
       
        apellidoEmpl.setBounds(10, 75, 100, 100);
        apellidoEmpl.setOpaque(false);
        apellidoEmpl.setVisible(true);
        apellidoEmpl.setFont(new Font("arial",Font.BOLD,14));
        JTextField apellidoEmplTxt = new JTextField();
        panel.add(apellidoEmplTxt);
        apellidoEmplTxt.setBounds(100, 115, 300, 20);
        
        edadEmpl.setBounds(10, 100, 100, 100);
        edadEmpl.setOpaque(false);
        edadEmpl.setVisible(true);
        edadEmpl.setFont(new Font("arial",Font.BOLD,14));
        JTextField edadEmplTxt = new JTextField();
        panel.add(edadEmplTxt);
        edadEmplTxt.setBounds(100, 140, 300, 20);
        
        sueldoEmpl.setBounds(10, 125, 100, 100);
        sueldoEmpl.setOpaque(false);
        sueldoEmpl.setVisible(true);
        sueldoEmpl.setFont(new Font("arial",Font.BOLD,14));
        JTextField sueldoEmplTxt = new JTextField();
        panel.add(sueldoEmplTxt);
        sueldoEmplTxt.setBounds(100, 165, 300, 20);
        
        //Agrego evento actionListener boton selectPers
            ActionListener editPers = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                selectPers.setEditable(true);
            }};
        selectPers.addActionListener(editPers);
        
        //Agrego evento actionListener boton limpiar
            ActionListener clear = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
              nEmplTxt.setText(null);
              sucPersTxt.setText(null);
              nombreEmplTxt.setText(null);
              apellidoEmplTxt.setText(null);
              edadEmplTxt.setText(null);
              sueldoEmplTxt.setText(null);
            }};
        cleanPers.addActionListener(clear);
        
           //Agrego evento actionListener boton visualizarPers
            ActionListener PersVer = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {  
                 //muestro lo escrito en los textfields al Jtable
                filasPers[0]=nEmplTxt.getText();
                filasPers[1]=sucPersTxt.getText();
                filasPers[2]=nombreEmplTxt.getText();
                filasPers[3]=apellidoEmplTxt.getText();
                filasPers[4]=edadEmplTxt.getText();
                filasPers[5]=sueldoEmplTxt.getText();
                dtm3.addRow(filasPers);                                 //añade fila de datos
                selectPers.addItem(filasPers[0].toString());            //añade los registros al combobox seleccionar
                   
                //**esta rompiendo la base de datos personal**
                /*//mostrar base de datos desde MySQL
                Conexion con = new Conexion();          //objeto clase Conexion
                String consulta = "SELECT * FROM restaurantebd.personal";
                Connection cn = con.getConnection();                        //conector
                
                try {
                    Statement st = cn.createStatement();
                    
                    //declaracion ResultSet y ejecucion de consulta
                    ResultSet rs= st.executeQuery(consulta);                    
                    
                    //vacio los datos en la tabla dtm3
                    while(rs.next()){
                        filasPers[0] = rs.getObject("numeroDeEmpleado");
                        filasPers[1] = rs.getObject("sucursal");
                        filasPers[2] = rs.getObject("nombre");
                        filasPers[3] = rs.getObject("apellido");
                        filasPers[4] = rs.getObject("edad");
                        filasPers[5] = rs.getObject("sueldo");
                    }
                    //tablePers.setModel(dtm3);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }*/
            }};
            seePers.addActionListener(PersVer);       
        
            //Agrego evento actionListener boton capturarPErs para conexion bd
            ActionListener cap = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                Conexion con = new Conexion();          //objeto clase Conexion
                Connection cn = con.getConnection();    //debe guardar datos en bd
                    String sql2= "";
                    sql2 = "INSERT INTO restaurantebd.personal (numeroDeEmpleado, sucursal,"
                            + "nombre, apellido, edad, sueldo) VALUES (?,?,?,?,?,?)";     //para guardar en columnas en la tabla precreada en el SGBD
                    try {
                        //Envio statements
                        PreparedStatement psd = cn.prepareStatement(sql2);
                        psd.setObject(1, filasPers[0]);
                        psd.setObject(2, filasPers[1]);
                        psd.setObject(3, filasPers[2]);
                        psd.setObject(4, filasPers[3]);
                        psd.setObject(5, filasPers[4]);
                        psd.setObject(6, filasPers[5]);
                        
                        //mensaje de guardado exitoso
                        int n = psd.executeUpdate();
                        if (n>0) {
                            JOptionPane.showMessageDialog(null, "Guardado");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }};
        capturarPers.addActionListener(cap);
        
        //Agrego evento actionListener boton exitPers
            ActionListener bBackPers = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {
                complementos();
                capturarInv.setVisible(false);          
                cleanInv.setVisible(false);
                seeInv.setVisible(false);
                exitInv.setVisible(false);
                selectInv.setVisible(false);
                nEmplTxt.setVisible(false);
                sucPersTxt.setVisible(false);
                nombreEmplTxt.setVisible(false);
                apellidoEmplTxt.setVisible(false);
                edadEmplTxt.setVisible(false);
                sueldoEmplTxt.setVisible(false);
                nEmpl.setVisible(false);
                sucPers.setVisible(false);
                nombreEmpl.setVisible(false);
                apellidoEmpl.setVisible(false);
                edadEmpl.setVisible(false);
                sueldoEmpl.setVisible(false);
                scrollPanePers.setVisible(false);
                barra.setVisible(true);
            }};
        exitPers.addActionListener(bBackPers); 
    }
        
    public void reporte(){
        Object[] ColRepo = {"Artículo", "Precio", "Cantidad", "Número de Empleado", "Sucursal", "Nombre", "Apellido", "Sueldo"};
        Object[] filasRepo = new Object[8]; 

        tableRepo.setColumnIdentifiers(ColRepo);                //establesco columnas
        JTable table = new JTable(tableRepo);
        JScrollPane scrollPane = new JScrollPane(table); 
        table.setPreferredScrollableViewportSize(new Dimension(100,250));
        getContentPane().add(scrollPane, BorderLayout.NORTH);

        //configuracion etiqueta
        panel.add(repoSuc);
        repoSuc.setText("Elija sucursal");
        repoSuc.setBounds(310, 100, 100, 40);
        repoSuc.setFont(new Font("arial",Font.BOLD,14));
        repoSuc.setVisible(true);
        
        //Agrego evento actionListener boton reporte
            ActionListener report = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {               

                //objeto clase Conexion
                Conexion con = new Conexion();         
                String consulta = "SELECT DISTINCT articulo, precio, cantidad, numeroDeEmpleado, personal.sucursal, nombre, apellido, sueldo FROM ventas JOIN personal;";       
                
                //conector
                Connection cn = con.getConnection();                      
                
                try {
                    Statement st = cn.createStatement();
                    
                    //declaracion ResultSet y ejecucion de consulta
                    ResultSet rs= st.executeQuery(consulta); 
                    
                    //vacio los datos en la tabla reporte(ventas + personal)
                    while(rs.next()){
                        filasRepo[0] = rs.getString("ventas.articulo");
                        filasRepo[1] = rs.getString("ventas.precio");
                        filasRepo[2] = rs.getString("ventas.cantidad");
                        filasRepo[3] = rs.getString("personal.numeroDeEmpleado");
                        filasRepo[4] = rs.getString("personal.sucursal");
                        filasRepo[5] = rs.getString("personal.nombre");
                        filasRepo[6] = rs.getString("personal.apellido");
                        filasRepo[7] = rs.getString("personal.sueldo");
                        table.setModel(tableRepo);
                        //agrego filas
                        tableRepo.addRow(filasRepo);
                    }                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }};
        repo.addActionListener(report);
        
         //Agrego evento actionListener boton imprimir
            ActionListener printRepo = new ActionListener (){                       
            @Override
            public void actionPerformed(ActionEvent ae) {               
               MessageFormat header = new MessageFormat("Reporte");     //Titulo de pagina al imprimir
               MessageFormat footer = new MessageFormat("Página");
               
               //trycatch de impresion usando mi tabla mostrada en app
                try {
                    table.print(JTable.PrintMode.FIT_WIDTH, header, footer);    
                } catch (Exception e) {
                    JOptionPane.showInternalMessageDialog(null, "No pude imprimir");
                }
            }};
        print.addActionListener(printRepo);
        
       //Agrego evento actionListener boton exitReport
        ActionListener bBackReport = new ActionListener (){                       
        @Override
        public void actionPerformed(ActionEvent ae) {
            complementos();
            exitReport.setVisible(false);          
            print.setVisible(false);
            selectSuc.setVisible(false);
            repoSuc.setVisible(false);
            barra.setVisible(true);
            repo.setVisible(false);
            scrollPane.setVisible(false);
        }};
        exitReport.addActionListener(bBackReport); 
    }
                         
    public void complementos() {
        //diseño de botones
        ImageIcon ouro = new ImageIcon(new ImageIcon("buttonTest.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));
        ImageIcon visual = new ImageIcon(new ImageIcon("botonVisualizar.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));
        ImageIcon guardar = new ImageIcon(new ImageIcon("botonGuardar.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));
        ImageIcon limpiar = new ImageIcon(new ImageIcon("botonLimpiar.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));
        ImageIcon printLogo = new ImageIcon(new ImageIcon("botonPrint.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));
        ImageIcon repoLogo = new ImageIcon(new ImageIcon("botonRepo.png").getImage().getScaledInstance(180, 40, Image.SCALE_DEFAULT));

        //colores panel
        this.getContentPane().add(panel);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
       
        //configuracion boton GUARDAR
        panel.add(save);        
        save.setBounds(310, 300, 180, 40);
        save.setVisible(false);
        save.setIcon(guardar);
                
        //configuracion boton limpiar
        panel.add(clean);        
        clean.setBounds(310, 400, 180, 40);
        clean.setVisible(false);
        clean.setIcon(limpiar);
        
        //configuracion boton Visualizar
        panel.add(see);        
        see.setBounds(310, 250, 180, 40);
        see.setVisible(false);
        see.setIcon(visual);
            
        //configuracion boton Seleccionar
        panel.add(select);        
        select.setBounds(310, 350, 180, 40);
        select.setVisible(false);
        select.setBackground(new Color(152,251,152));
        
        //configuracion boton Salir
        panel.add(exit);        
        exit.setBounds(310, 450, 180, 40);
        exit.setText("Salir");
        exit.setFont(new Font("consolas", Font.BOLD, 18));
        exit.setVisible(false);
        exit.setBackground(new Color(221,160,221));

        
        //configuracion boton capturarInv
        panel.add(capturarInv);        
        capturarInv.setBounds(310, 300, 180, 40);
        capturarInv.setVisible(false);
        capturarInv.setIcon(guardar);
        
        //configuracion boton cleanInv
        panel.add(cleanInv);        
        cleanInv.setBounds(310, 400, 180, 40);
        cleanInv.setVisible(false);
        cleanInv.setIcon(limpiar);
        
        //configuracion boton seeInv
        panel.add(seeInv);        
        seeInv.setBounds(310, 250, 180, 40);
        seeInv.setVisible(false);
        seeInv.setIcon(visual);
            
        //configuracion boton SeleccionarInv
        panel.add(selectInv);        
        selectInv.setBounds(310, 350, 180, 40);
        selectInv.setVisible(false);
        selectInv.setBackground(new Color(152,251,152));
        
         //configuracion boton exitInv
        panel.add(exitInv);        
        exitInv.setBounds(310, 450, 180, 40);
        exitInv.setVisible(false);
        exitInv.setIcon(ouro);
        
        //configuracion boton capturarPers
        panel.add(capturarPers);        
        capturarPers.setBounds(310, 300, 180, 40);
        capturarPers.setVisible(false);
        capturarPers.setIcon(guardar);

        //configuracion boton cleanPers
        panel.add(cleanPers);        
        cleanPers.setBounds(310, 400, 180, 40);
        cleanPers.setVisible(false);
        cleanPers.setIcon(limpiar);
        
        //configuracion boton seePers
        panel.add(seePers);        
        seePers.setBounds(310, 250, 180, 40);
        seePers.setVisible(false);
        seePers.setIcon(visual);
            
        //configuracion boton selectPers
        panel.add(selectPers);        
        selectPers.setBounds(310, 350, 180, 40);
        selectPers.setVisible(false);
        selectPers.setBackground(new Color(152,251,152));
        
         //configuracion boton exitPers
        panel.add(exitPers);        
        exitPers.setBounds(310, 450, 180, 40);
        exitPers.setVisible(false);
        exitPers.setIcon(ouro);
        
        //configuracion boton selectSuc
        panel.add(selectSuc);        
        selectSuc.setBounds(310, 150, 180, 40);
        selectSuc.setVisible(false);
        selectSuc.setBackground(new Color(152,251,152));
        //selectSuc.setIcon(ouro);
        
        //configuracion boton print
        panel.add(print);        
        print.setBounds(310, 250, 180, 40);
        print.setVisible(false);
        print.setIcon(printLogo);
        
        //configuracion boton reporte
        panel.add(repo);        
        repo.setBounds(310, 200, 180, 40);
        repo.setVisible(false);
        repo.setIcon(repoLogo);
         
         //configuracion boton exitReport
        panel.add(exitReport);        
        exitReport.setBounds(310, 300, 180, 40);
        exitReport.setVisible(false);
        exitReport.setIcon(ouro);
        
        //configuracion boton Salir
        panel.add(exit);        
        exit.setBounds(310, 450, 180, 40);
        exit.setVisible(false);
        ouro = new ImageIcon(new ImageIcon("buttonTest.png").getImage().getScaledInstance(220, 40, Image.SCALE_DEFAULT));   //se bugueo el tamaño x
        exit.setIcon(ouro);
       
        //imagen de fondo
        ImageIcon menu = new ImageIcon(new ImageIcon("main2.png").getImage().getScaledInstance(800,710, Image.SCALE_REPLICATE));
        panel.add(end);
        end.setBounds(0, 50, 800, 710);
        end.setOpaque(false);
        end.setVisible(true);
        end.setIcon(menu); 
        
        //fondo2
        ImageIcon fondoV = new ImageIcon(new ImageIcon("paisaje.jpg").getImage().getScaledInstance(800,710, Image.SCALE_REPLICATE));
        panel.add(fondo);
        fondo.setBounds(0, 50, 800, 710);
        fondo.setOpaque(false);
        fondo.setVisible(true);
        fondo.setIcon(fondoV); 
      }
    
    //configuracion combobox de submenu reporte
    private void repoCombo(){
        Conexion con = new Conexion();       
        Connection cn = con.getConnection();                          

        try {
        String selecSuc = "select sucursal from personal";            
        PreparedStatement st = cn.prepareStatement(selecSuc);

        //declaracion ResultSet y ejecucion de consulta
        ResultSet rs= st.executeQuery(selecSuc); 

        //vacio los datos en comobox
        while(rs.next()){
            String funciona = rs.getString("sucursal");
            selectSuc.addItem(funciona);
        }
        }catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
        }
    }
}