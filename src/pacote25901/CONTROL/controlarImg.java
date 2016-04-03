package pacote25901.CONTROL;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pacote25901.VIEW.interfaceGrafica;

public class controlarImg {
	private	char[][]      imagemCinza;
	private int           nLinImagem;
	private int           nColImagem;
	private BufferedImage imagemDada;

	//*******************************************************************************************
	public controlarImg( String   nomeArquivoImagemDada, Graphics desenho)
	{
		imagemDada = lerImagem ( nomeArquivoImagemDada );
		if ( imagemDada != null ) {
			mostrarImagemBuffer ( imagemDada, desenho );
			criarImagemCinza ( imagemDada );
		}
	}

	//*******************************************************************************************
	// METODO PARA GERAR A IMAGEM RASTER EM NIVEIS DE CINZA A PARTIR DA IMAGEM BUFERIZADA COLORIDA

	private void criarImagemCinza ( BufferedImage imagem ) 
	{
		int    x, y, r, g, b;
		Raster imagemRasterEntrada;
		char   valorSaida;

		// DIMENSOES DA MATRIZ CINZA
		nColImagem  = imagem.getWidth(null);
		nLinImagem  = imagem.getHeight(null);
		imagemCinza = new char[nColImagem][nLinImagem];

		// DEFININDO IMAGENS INTERMEDIARIAS
		imagemRasterEntrada = imagem.getRaster();

		// CRIANDO IMAGEM CINZA
		for ( y = 0; y < nLinImagem; y++ ) {
			for ( x = 0; x < nColImagem; x++ ) {

				// LENDA DADOS DAS BANDAS DA IMAGEM DADA
				r = imagemRasterEntrada.getSample(x,y,0);  // le dado da banda 0 da imagem de entrada 

				try {
					g = imagemRasterEntrada.getSample(x,y,1);  // le dado da banda 1 da imagem de entrada 
					b = imagemRasterEntrada.getSample(x,y,2);  // le dado da banda 2 da imagem de entrada
				} catch ( ArrayIndexOutOfBoundsException e) {
					g = r;
					b = r;
				}

				//  GERANDO NIVEL DE CINZA 
				valorSaida = (char)((r+g+b)/3);
				imagemCinza[x][y] = valorSaida;
			}
		}
	}
	
	//******************************************************************************************
	public BufferedImage getImagem ( )
	{

		return ( imagemDada );
	}
		
	//******************************************************************************************
	public char[][] copiarImagem ( char[][] imagemC,
			                       int      nLinImg, 
			                       int      nColImg
			                     )
	{
		int      x, y;
		char[][] imagemR;

		imagemR = new char[nColImg][nLinImg];

		for ( x = 0; x < nColImg; x++ ) 
			for ( y = 0; y < nLinImg; y++ )
				imagemR[x][y] = imagemC[x][y];

		return ( imagemR );
	}

	//*******************************************************************************************
	private BufferedImage lerImagem ( String nomeArquivo )
	{
		File          arquivoImagem;
		BufferedImage imagem;

		// INICIALIZANDO VARIAVEIS
		imagem        = null;
		arquivoImagem = new File(nomeArquivo);

		// LEITURA DA IMAGEM
		try {
			imagem = ImageIO.read( arquivoImagem );
		} catch (IOException e) {
			System.out.println ( "imagem " + nomeArquivo + " nao existe");
		}

		return ( imagem );
	}

	//*******************************************************************************************
	// MOSTRAR IMAGEM BUFERIZADA

	public void mostrarImagemBuffer  ( BufferedImage imagem,
			                           Graphics      desenho 
			                         )
	{
		int imageWidth, imageHeight, x, sx, y, sy, cell, dx, dy;
		int cells[] = {0, 1, 2, 3};

		imageWidth  = imagem.getWidth(null);
		imageHeight = imagem.getHeight(null);

		for ( x = 0; x < 2; x++ ) {
		    sx = x * imageWidth;
		    for ( y = 0; y < 2; y++ ) {
		        sy   = y * imageHeight;
		        cell = cells[x*2+y];
		        dx   = (cell / 2) * imageWidth;
		        dy   = (cell % 2) * imageHeight;
		        desenho.drawImage( imagem, dx, dy, x + imageWidth, dy + imageHeight,  
		        		           sx, sy,  sx + imageWidth, sy + imageHeight, null );
		    }
		}
	}

	//*******************************************************************************************
	// MOSTRAR IMAGEM DO TIPO MATRIZ DE BYTES

	public void mostrarImagemMatriz  ( char[][] imagemM,
			                           int      nLin,
			                           int      nCol,
                                       Graphics desenho 
                                     )
	{
		BufferedImage imagemB;

		imagemB = transformarMatriz2Buffer ( imagemM, nLin, nCol );
		desenho.drawImage( imagemB, 0, 0, nCol, nLin,  null );  
	}

	//*******************************************************************************************
	private BufferedImage transformarMatriz2Buffer ( char[][] imagemM,
			                                         int      nLin,
			                                         int      nCol
			                                       )
	{
		int            x, y;
		char           valorSaida;
		WritableRaster imagemRasterSaida;
		BufferedImage  imagemB;

		imagemB           = new BufferedImage( nCol, nLin, BufferedImage.TYPE_BYTE_GRAY ); 
		imagemRasterSaida = imagemB.getRaster();

		for ( y = 0; y < nLin; y++ ) {
			for ( x = 0; x < nCol; x++ ) {
				valorSaida = imagemM[x][y];
				imagemRasterSaida.setSample( x, y, 0, valorSaida );
			}
		}

		return ( imagemB );
	}

	//*******************************************************************************************
	public void gravarImagem ( String   nomeArquivo,
			                   char[][] imagemM,
			                   int      nLin,
                               int      nCol
			                 )
	{
		File          arquivoImagem;
		BufferedImage imagemB;

		imagemB = transformarMatriz2Buffer ( imagemM, nLin, nCol );

		// INICIALIZANDO VARIAVEIS
		arquivoImagem = new File(nomeArquivo + ".jpg" );

		// LEITURA DA IMAGEM
		try {
			ImageIO.write( imagemB, "jpg", arquivoImagem );
		} catch (IOException e) {
			System.out.println ( "imagem " + nomeArquivo + " nao existe");
		}
	}
	
	//*******************************************************************************************
		public void interpolacaoImagem(float tamanho, int metodo, interfaceGrafica pnCenario, Graphics desenhoDir)
		{
			if (metodo == 1){
				
				if (tamanho<1){
					pnCenario.limpaPainelDir( desenhoDir );
					mostrarImagemBuffer ( vizinhoMaisProximo1(getImagem(), tamanho), desenhoDir );
				}
				else{
					pnCenario.limpaPainelDir( desenhoDir );
					mostrarImagemBuffer ( vizinhoMaisProximo(getImagem(), (int)tamanho), desenhoDir );
				}
				
			}
			
			if (metodo == 2){
				BufferedImage imagem;
				imagem = getImagem();
				int w = nColImagem;
				int h = nLinImagem;
				int w1 = (int)(w*tamanho);
				int h1 = (int)(h*tamanho);

				BufferedImage bimage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB);

			    // Draw the image on to the buffered image
			    Graphics2D bGr = bimage.createGraphics();
			    bGr.drawImage(imagem.getScaledInstance((int)(tamanho*(imagem.getWidth())), (int)(tamanho*(imagem.getHeight())), 1), 0, 0, null);
			    bGr.dispose();
			    
				BufferedImage imagemBicubica = new BufferedImage(bimage.getWidth(null), bimage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

				int novoWidth = bimage.getWidth();
				int novoHeight = bimage.getHeight();
				for (int y = 4; y < novoHeight - 4; y++){	
			    	for (int x = 4; x < novoWidth - 4; x++){
			            int valueRGB = cubica(bimage, x, y);
			            imagemBicubica.setRGB(x, y, valueRGB);
			    	}
			    }
				
				Graphics2D g2 = imagemBicubica.createGraphics();
			    g2.drawImage(imagemBicubica, 0, 0, null);
			    g2.dispose();
			    
			    pnCenario.limpaPainelDir( desenhoDir );
				mostrarImagemBuffer ( imagemBicubica, desenhoDir );
			}
			
			if (metodo == 3){
				
				BufferedImage imagem;
				imagem = getImagem();
				int w = nColImagem;
				int h = nLinImagem;
				int w1 = (int)(w*tamanho);
				int h1 = (int)(h*tamanho);

				BufferedImage bimage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB);

			    // Draw the image on to the buffered image
			    Graphics2D bGr = bimage.createGraphics();
			    bGr.drawImage(imagem.getScaledInstance((int)(tamanho*(imagem.getWidth())), (int)(tamanho*(imagem.getHeight())), 1), 0, 0, null);
			    bGr.dispose();
			    
				BufferedImage imagemBicubica = new BufferedImage(bimage.getWidth(null), bimage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

				int novoWidth = bimage.getWidth();
				int novoHeight = bimage.getHeight();
				for (int y = 4; y < novoHeight - 4; y++){	
			    	for (int x = 4; x < novoWidth - 4; x++){
			            int valueRGB = bicubica(bimage, x, y);
			            imagemBicubica.setRGB(x, y, valueRGB);
			    	}
			    }
				
				Graphics2D g2 = imagemBicubica.createGraphics();
			    g2.drawImage(imagemBicubica, 0, 0, null);
			    g2.dispose();
			    
			    pnCenario.limpaPainelDir( desenhoDir );
				mostrarImagemBuffer ( imagemBicubica, desenhoDir );
			}
			
			
		}
		
		public int interpolacaoCubica (int A, int B, int C, int D, float t)
		{
			int red=0, green=0, blue=0;
			
			red += (A & 0x00ff0000) >> 16;
			green += (A & 0x0000ff00) >> 8;
			blue += A & 0x000000ff;			
			red += (B & 0x00ff0000) >> 16;
			green += (B & 0x0000ff00) >> 8;
			blue += B & 0x000000ff;			
			red += (C & 0x00ff0000) >> 16;
			green += (C & 0x0000ff00) >> 8;
			blue += C & 0x000000ff;			
			red += (D & 0x00ff0000) >> 16;
			green += (D & 0x0000ff00) >> 8;
			blue += D & 0x000000ff;
			
			Color color = new Color(red/4, green/4, blue/4);
	        int rgb = color.getRGB();
			
		    return rgb;
		}
		
		public int cubica (BufferedImage imagem, int x, int y)
		{

			int p00 = imagem.getRGB(x - 1, y);
		    int p10 = imagem.getRGB(x + 0, y);
		    int p20 = imagem.getRGB(x + 1, y);
		    int p30 = imagem.getRGB(x + 2, y);
		 
	        int col = interpolacaoCubica(p00, p10, p20, p30, (float) 0.5);
	        
	        int valueRGB = (int) col;

		    return valueRGB;
		}
		
		public int bicubica (BufferedImage imagem, int x, int y)
		{

			int p00 = imagem.getRGB(x - 1, y - 1);
		    int p10 = imagem.getRGB(x + 0, y - 1);
		    int p20 = imagem.getRGB(x + 1, y - 1);
		    int p30 = imagem.getRGB(x + 2, y - 1);
		    int p01 = imagem.getRGB(x - 1, y + 0);
		    int p11 = imagem.getRGB(x + 0, y + 0);
		    int p21 = imagem.getRGB(x + 1, y + 0);
		    int p31 = imagem.getRGB(x + 2, y + 0);
		    int p02 = imagem.getRGB(x - 1, y + 1);
		    int p12 = imagem.getRGB(x + 0, y + 1);
		    int p22 = imagem.getRGB(x + 1, y + 1);
		    int p32 = imagem.getRGB(x + 2, y + 1);
		    int p03 = imagem.getRGB(x - 1, y + 2);
		    int p13 = imagem.getRGB(x + 0, y + 2);
		    int p23 = imagem.getRGB(x + 1, y + 2);
		    int p33 = imagem.getRGB(x + 2, y + 2);
		 

	        int col0 = interpolacaoCubica(p00, p10, p20, p30, (float) 0.5);
	        int col1 = interpolacaoCubica(p01, p11, p21, p31, (float) 0.5);
	        int col2 = interpolacaoCubica(p02, p12, p22, p32, (float) 0.5);
	        int col3 = interpolacaoCubica(p03, p13, p23, p33, (float) 0.5);
	        int value = interpolacaoCubica(col0, col1, col2, col3, (float) 0.5);
	        
	        int valueRGB = (int) value;

		    return valueRGB;
		}
		
		public BufferedImage vizinhoMaisProximo(BufferedImage imagem, int fator){
			int w = nColImagem;
			int h = nLinImagem;
			BufferedImage imagem2 = new BufferedImage(w*fator, h*fator, BufferedImage.TYPE_INT_RGB);
			for(int i=0; i<w*fator; i++){
				for(int j=0; j<h*fator; j++){
					imagem2.setRGB(i, j, imagem.getRGB((int)i/fator, (int)j/fator));
				}
			}
			return imagem2;
		}
		
		public BufferedImage vizinhoMaisProximo1(BufferedImage imagem, float fator){
			int w = nColImagem;
			int h = nLinImagem;
			int w1 = (int)(w*fator);
			int h1 = (int)(h*fator);
			BufferedImage imagem2 = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
			for(int i=0; i<(int)(w*fator); i++){
				for(int j=0; j<(int)(h*fator); j++){
					imagem2.setRGB(i, j, imagem.getRGB((int)(i/fator), (int)(j/fator)));
				}
			}
			return imagem2;
		}

	//*******************************************************************************************
	public char[][] getImagemCinza ( )
	{
		return ( imagemCinza );
	}

	//*******************************************************************************************
	public int getNLin()
	{
		return ( nLinImagem );
	}

	//*******************************************************************************************
	public int getNCol()
	{
		return ( nColImagem );
	}

	//*******************************************************************************************
}
