package pacote25901.VIEW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pacote25901.CONTROL.controlarApp;

public class interfaceGrafica {
	private JFrame   baseFrame;
	private JPanel   basePanel;
	private JPanel   pSaida, pSaidaEsq, pSaidaDir, pSaidaCen;
	private JPanel   controlePanelAcao1;
	private JPanel   controlePanelAcao2;
	private JPanel   controlePanelAcao3;

	private JButton  bInterpolar;
	private JButton  bSalva;
	private JButton  bReset;

	private JRadioButton  bVMP;
	private JRadioButton  bCubica;
	private JRadioButton  bBicubica;
	private ButtonGroup   bRdInterpolacoes;

	private JRadioButton  b02, b06, b08, b2, b4, b5;
	private ButtonGroup   bRE;

	private JRadioButton  bVisualAllImg;

	private Graphics      desenhoCen;
	private Graphics      desenhoDir;

	//*******************************************************************************************
	public interfaceGrafica( controlarApp controlePrograma )
	{
		JPanel  buttonPanel;
		JPanel  titlePanel;
		JPanel  acao1Panel;
		JPanel  acao2Panel;

		// LAYOUT
		baseFrame = new JFrame();
		baseFrame.setLayout( new BoxLayout( baseFrame.getContentPane(), BoxLayout.Y_AXIS) );

		baseFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // FITS PANEL TO THE ACTUAL MONITOR SIZE
		baseFrame.setUndecorated(true);  // TURN OFF ALL THE PANEL BORDERS 

		basePanel = new JPanel();
		basePanel.setLayout( new BorderLayout() );

		// TITLE PANEL
		titlePanel = new JPanel();
		titlePanel.setPreferredSize( new Dimension ( 0, 50 ) );
		titlePanel.setBackground( Color.gray );

		// OUTPUT PANEL
		pSaida = new JPanel();
		pSaida.setLayout( new BorderLayout() );

		pSaidaEsq = new JPanel();
		pSaidaEsq.setPreferredSize( new Dimension ( 130, 0 ) );
		pSaidaEsq.setLayout( new BoxLayout (pSaidaEsq, BoxLayout.Y_AXIS));
		pSaidaEsq.setBackground( Color.GRAY );

		pSaidaCen = new JPanel();
		pSaidaCen.setBackground( new Color ( 220, 220, 210 ) );
		pSaidaCen.setLayout( new BorderLayout() );

		pSaidaDir = new JPanel();
		pSaidaDir.setBackground( new Color ( 210, 200, 200 ) );
		pSaidaDir.setPreferredSize( new Dimension ( 750, 0 ) );
		pSaidaDir.setLayout( new BorderLayout() );

		// BUTTON PANEL
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize( new Dimension ( 0, 40 ) );
		buttonPanel.setBackground( Color.gray );

		// PANEL TITLE
		JLabel titulo;
		titulo = new JLabel( "Exercício 3 - Escalamento | Adan Ricardo Santos - 25901");
		titulo.setForeground(Color.black);
		titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		titlePanel.add(titulo);

		// ADDING BUTTONS
		addAButton ( "Abrir Imagem", "botaoImagem", buttonPanel, true, controlePrograma );
		bReset = addAButton ( "Resetar", "botaoReset", buttonPanel, false, controlePrograma );
		bInterpolar = addAButton ( "Interpolar", "botaoInterpolar", buttonPanel, false, controlePrograma );
		bSalva = addAButton ( "Salvar", "botaoSalva", buttonPanel, false, controlePrograma );
		addAButton ( "Sair", "botaoFim", buttonPanel, true, controlePrograma );

		// ADDING RADIO BUTTON PARA CONTROLE DA ACAO 1
		controlePanelAcao1 = new JPanel();
		controlePanelAcao1.setBackground( Color.lightGray );
		controlePanelAcao1.setMaximumSize( new Dimension ( 130, 115 ) );
		pSaidaEsq.add( controlePanelAcao1 );

		bVMP = new JRadioButton ( "Vizinhos Próximos", true );
		bCubica = new JRadioButton ( "Cubica", false );
		bBicubica = new JRadioButton ( "Bicubica", false );

		bRdInterpolacoes = new ButtonGroup();
		bRdInterpolacoes.add(bVMP );
		bRdInterpolacoes.add(bCubica );		
		bRdInterpolacoes.add(bBicubica );		

		bVMP.addActionListener(controlePrograma);
		bCubica.addActionListener(controlePrograma);
		bBicubica.addActionListener(controlePrograma);

		acao1Panel = new JPanel();
		acao1Panel.setPreferredSize( new Dimension ( 130, 110 ) );
		acao1Panel.setLayout(new GridLayout(5, 1));

		acao1Panel.add( bVMP );
		acao1Panel.add( bCubica );
		acao1Panel.add( bBicubica );

		acao1Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Interpolação"));

		controlePanelAcao1.add(acao1Panel);
		controlePanelAcao1.setVisible(false);

		controlePanelAcao2 = new JPanel();
		controlePanelAcao2.setBackground( Color.lightGray );
		controlePanelAcao2.setMaximumSize( new Dimension ( 130, 135 ) );
		pSaidaEsq.add( controlePanelAcao2 );
		
		b02 = new JRadioButton ( "0.3x", true );
		b06 = new JRadioButton ( "0.6x", false );
		b08 = new JRadioButton ( "0.9x", false );
		b2 = new JRadioButton ( "2x", false );
		b4 = new JRadioButton ( "3x", false );
		b5 = new JRadioButton ( "4x", false );

		bRE = new ButtonGroup();
		bRE.add(b02);
		bRE.add(b06);
		bRE.add(b08);
		bRE.add(b2);
		bRE.add(b4);
		bRE.add(b5);
		
		b02.addActionListener(controlePrograma);
		b06.addActionListener(controlePrograma);
		b08.addActionListener(controlePrograma);
		b2.addActionListener(controlePrograma);
		b4.addActionListener(controlePrograma);
		b5.addActionListener(controlePrograma);

		b02.setActionCommand( "bE03" );
		b06.setActionCommand( "bE06" );
		b08.setActionCommand( "bE09" );
		b2.setActionCommand( "bE2" );
		b4.setActionCommand( "bE3" );
		b5.setActionCommand( "bE4" );
		
		acao2Panel = new JPanel();
		acao2Panel.setPreferredSize( new Dimension ( 130, 130 ) );
		acao2Panel.setLayout(new GridLayout(6, 1));
		
		acao2Panel.add( b02 );
		acao2Panel.add( b06 );
		acao2Panel.add( b08 );
		acao2Panel.add( b2 );
		acao2Panel.add( b4 );
		acao2Panel.add( b5 );

		acao2Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fator"));

		controlePanelAcao2.add(acao2Panel);
		controlePanelAcao2.setVisible(false);

		pSaida.add( pSaidaEsq, BorderLayout.LINE_START );
		pSaida.add( pSaidaCen, BorderLayout.CENTER );
		pSaida.add( pSaidaDir, BorderLayout.LINE_END );

		basePanel.add( titlePanel, BorderLayout.PAGE_START );
		basePanel.add( pSaida, BorderLayout.CENTER );
		basePanel.add( buttonPanel, BorderLayout.PAGE_END );

		baseFrame.add(basePanel);
		baseFrame.setVisible(true);
	}

	//*******************************************************************************************
	public void limpaPainelCen ( Graphics desenho )
	{
		pSaidaCen.removeAll();
		pSaidaCen.update( desenho );
	}

	//*******************************************************************************************
	public void limpaPainelDir ( Graphics desenho )
	{
		pSaidaDir.removeAll();
		pSaidaDir.update( desenho );
	}

	//*******************************************************************************************
	// METODO UTILIZADO PARA ADICIONAR UM BOTAO A UM CONTAINER DO PROGRAMA

	private JButton addAButton( String              textoBotao,
                                String              textoControle,
                                Container           container,
                                boolean             estado,
                                controlarApp controlePrograma
                              ) 
	{
		JButton botao;

		botao = new JButton( textoBotao );
		botao.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(botao);

		botao.setEnabled(estado);

		botao.setActionCommand( textoControle );

		botao.addActionListener(controlePrograma);

		return ( botao );
	}

	//*******************************************************************************************
	public void mudarBotoes() 
	{
		bInterpolar.setEnabled(true);
		bSalva.setEnabled(true);
		bReset.setEnabled(true);
		controlePanelAcao1.setVisible(true);
		controlePanelAcao2.setVisible(true);
	}

	//*******************************************************************************************
	// METODO PARA APRESENTAR O MENU DE ESCOLHA DE ARQUIVOS
	// 1 - PARA LEITURA
	// 2 - PARA GRAVACAO
	
	public String escolherArquivo ( int operacao )   
	{
		int          retorno;
		String       caminhoArquivo;
		JFileChooser arquivo;

		retorno = 0;
		arquivo = new JFileChooser(new File("."));

		// TIPO DE OPERACAO A SER REALIZADA
		switch ( operacao ) {
		case 1:
			retorno = arquivo.showOpenDialog(null);
			break;

		case 2:
			retorno = arquivo.showSaveDialog(null);
		}

		// OPERACAO
		caminhoArquivo = null;

		if(retorno == JFileChooser.APPROVE_OPTION){
			try {
				caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
			}	catch (ArrayIndexOutOfBoundsException e) {
			    System.out.println("erro: " + e);
			}
		} 

		return ( caminhoArquivo );
	}

	//*******************************************************************************************
	// METODO PARA MOSTRAR O FRAME BASICO

	public void showPanel() 
	{
		basePanel.setVisible(true);
	}

	//*******************************************************************************************
	public void ativarPainelAcao3()
	{
		controlePanelAcao3.setVisible(true);
	}

	//*******************************************************************************************
	public void desativarPainelAcao3()
	{
		controlePanelAcao3.setVisible(false);
	}

	//*******************************************************************************************
	public void ativarPainelAcao1()
	{
		controlePanelAcao1.setVisible(true);
	}

	//*******************************************************************************************
	public void desativarPainelAcao1()
	{
		controlePanelAcao1.setVisible(false);
	}

	//*******************************************************************************************
	public void iniciarGraphics()
	{
		desenhoCen = pSaidaCen.getGraphics();
		desenhoDir = pSaidaDir.getGraphics();
	}

	//*******************************************************************************************
	public Graphics getDesenhoC()
	{
		return ( desenhoCen );
	}

	//*******************************************************************************************
	public Graphics getDesenhoD()
	{
		return ( desenhoDir );
	}

	//******************************************************************************************
	public int getTipoVisualImage() 
	{
		int tipo;
		
		tipo = 1;
		if ( bVisualAllImg.isSelected() ) tipo = 2;

		return ( tipo );
	}

	//******************************************************************************************
	public void resetaSistema()
	{
		bVMP.setSelected(true);
		b02.setSelected(true);
		//bAcao31.setSelected(true);
	}
	
	//******************************************************************************************
	public void habilitarBotaoAplicar()
	{
		bInterpolar.setEnabled(true);
	}
}
