package pacote25901.CONTROL;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import pacote25901.CONTROL.controlarImg;
import pacote25901.VIEW.interfaceGrafica;

public class controlarApp implements ActionListener {
	private interfaceGrafica 	pnCenario;
	private Graphics            	desenhoCen, desenhoDir;
	private controlarImg     	ctrlImagem;
	private String              	nomeArquivoImagemDada;
	private char[][]            	imagemCinza;
	private char[][]            	imagemAtual;

	private int					metodo = 1;
	private float				fator = 2;
	private int                 nLinImageAtual, nColImageAtual;
	private int                 nLinImageInic, nColImageInic;
	private boolean             estadoDesenho;

	//*******************************************************************************************
	public controlarApp( )
	{
		pnCenario = new interfaceGrafica( this );
		pnCenario.showPanel();
		estadoDesenho  = false;
	}
 
	//*******************************************************************************************
	// METODO PARA CONTROLE DOS BOTOES DO APLICATIVO
	
	public void actionPerformed(ActionEvent e)
	{
		String comando, nomeArquivo;

		comando = e.getActionCommand();

		// DEFINE AMBIENTE GRAFICO
		if ( !estadoDesenho ) {
			pnCenario.iniciarGraphics();
			desenhoCen = pnCenario.getDesenhoC();
			desenhoDir = pnCenario.getDesenhoD();
		}

		// ENDS THE PROGRAM
		if(comando.equals("botaoFim")) {
			System.exit(0);	
		}

		if(comando.equals("botaoImagem")) {

			nomeArquivoImagemDada = pnCenario.escolherArquivo ( 1 );
			if ( nomeArquivoImagemDada != null ) {
				ctrlImagem = new controlarImg( nomeArquivoImagemDada, desenhoCen );
				estadoDesenho  = true;
				imagemCinza    = ctrlImagem.getImagemCinza();
				nLinImageInic  = ctrlImagem.getNLin();
				nColImageInic  = ctrlImagem.getNCol();

				pnCenario.mudarBotoes();
				pnCenario.limpaPainelDir( desenhoDir );

				pnCenario.habilitarBotaoAplicar();
			}
		}

		if ( comando.equals( "botaoInterpolar" )) {
			ctrlImagem.interpolacaoImagem(fator, metodo,pnCenario,desenhoDir);
		}

		if ( comando.equals( "Vizinhos" ) )  {
			metodo = 1;
		} 

		if ( comando.equals( "Cubica" ) ) {
			metodo = 2;
		}

		if ( comando.equals( "Bicubica" ) ) {
			metodo = 3;
		}

		if ( comando.equals( "bE03" ) ) {
			fator = 0.3f;
		}
		
		if ( comando.equals( "bE06" ) ) {
			fator = 0.6f;
		}

		if ( comando.equals( "bE09" ) ) {
			fator = 0.9f;
		}

		if ( comando.equals( "bE2" ) ) {
			fator = 2;
		}

		if ( comando.equals( "bE3" ) ) {
			fator = 3;
		}
		
		if ( comando.equals( "bE4" ) ) {
			fator = 4;
		}


		if ( comando.equals( "botaoSalva" ) && estadoDesenho ) {
			nomeArquivo = pnCenario.escolherArquivo ( 2 );
			ctrlImagem.gravarImagem( nomeArquivo, imagemAtual, nLinImageAtual, nColImageAtual );
		}

		if ( comando.equals( "botaoReset" ) && estadoDesenho ) {
			pnCenario.limpaPainelCen( desenhoCen );
			ctrlImagem = new controlarImg( nomeArquivoImagemDada, desenhoCen );
			nLinImageAtual   = nLinImageInic;
			nColImageAtual   = nColImageInic;
			imagemAtual      = ctrlImagem.copiarImagem ( imagemCinza, nLinImageInic, nColImageInic );

			pnCenario.limpaPainelDir( desenhoDir );
			
			pnCenario.ativarPainelAcao1();
			pnCenario.resetaSistema();
		}
	}
}
