package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class jogo extends ApplicationAdapter {

	//Texturas
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;
	private Texture gameOver;

	//Formas para colisao
	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoTopo;
	private Rectangle retanguloCanoBaixo;

	//Atributos de configurações
	private float alturaDispositivo;
	private float larguraDispositivo;
	private float variacao =0;
	private float gravidade = 0;
	private float posicaoInicialVerticalPassaro;
	private float posicaoHorizontalCano;
	private float posicaoVerticalCano;
	private float espacoEntreCanos = 130;
	private Random random;
	private int pontos = 0;
	private int pontuacaoMaxima = 0;
	private boolean passouCano = false;
	private int estadoJogo = 0;
	private float posicaoHorizontalPassaro = 0;

	//Exibicao de texto
    BitmapFont textoPontuacao;
    BitmapFont textoReiniciar;
    BitmapFont textoMelhorPontuacao;

    //Configuracao dos sons
	private Sound somVoando;
	private Sound somColisao;
	private Sound somPontuacao;

	//Objeto para salvar pontuacao
	Preferences preferencias;
	
	@Override
	public void create () {
		//Gdx.app.log("create","jogo iniciado");
		inicializarTexturas();
		inicializarObjetos();
	}

	@Override
	public void render () {
		desenharObjetos();
		validarPontos();
		verificarEstadoJogo();
		detectarColisoes();
	}

	private void desenharObjetos(){

		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[(int)variacao],50 + posicaoHorizontalPassaro,posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo,posicaoHorizontalCano,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos + posicaoVerticalCano);
		batch.draw(canoTopo,posicaoHorizontalCano,alturaDispositivo/2 + espacoEntreCanos + posicaoVerticalCano);
        textoPontuacao.draw(batch,String.valueOf(pontos),larguraDispositivo/2,alturaDispositivo-110);

        if (estadoJogo == 2){
        	batch.draw(gameOver,larguraDispositivo/2 - gameOver.getWidth()/2, alturaDispositivo/2);
        	textoReiniciar.draw(batch,"Toque na tela para reiniciar",larguraDispositivo/2 - 200,alturaDispositivo/2 - gameOver.getHeight()/2 );
        	textoMelhorPontuacao.draw(batch,"Melhor pontuação: "+pontuacaoMaxima,larguraDispositivo/2 - 140,alturaDispositivo/2-100);
		}

		batch.end();
	}

	private void detectarColisoes(){

		circuloPassaro.set(50+passaros[0].getWidth()/2+posicaoHorizontalPassaro,posicaoInicialVerticalPassaro+passaros[0].getHeight()/2,passaros[0].getWidth()/2);
		retanguloCanoTopo.set(posicaoHorizontalCano,alturaDispositivo/2 + espacoEntreCanos + posicaoVerticalCano,canoTopo.getWidth(),canoTopo.getHeight());
		retanguloCanoBaixo.set(posicaoHorizontalCano,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos + posicaoVerticalCano,canoBaixo.getWidth(),canoBaixo.getHeight());

		Boolean colidiuCanoTopo = Intersector.overlaps(circuloPassaro,retanguloCanoTopo);
		Boolean colidiuCanoBaixo = Intersector.overlaps(circuloPassaro,retanguloCanoBaixo);

		if (colidiuCanoTopo || colidiuCanoBaixo){
			if (estadoJogo == 1){
				somColisao.play();
				estadoJogo = 2;
			}

		}

		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(50+passaros[0].getWidth()/2,posicaoInicialVerticalPassaro+passaros[0].getHeight()/2,passaros[0].getWidth()/2);
		//Topo
		shapeRenderer.rect(posicaoHorizontalCano,alturaDispositivo/2 + espacoEntreCanos + posicaoVerticalCano,canoTopo.getWidth(),canoTopo.getHeight());
		//Baixo
		shapeRenderer.rect(posicaoHorizontalCano,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos + posicaoVerticalCano,canoBaixo.getWidth(),canoBaixo.getHeight());
		shapeRenderer.end();
		*/
	}

	private void validarPontos(){
		if (posicaoHorizontalCano <40){
			if (!passouCano){
				somPontuacao.play();
				pontos++;
				passouCano = true;
			}
		}

		//Bater de asas do pássaro
		variacao += Gdx.graphics.getDeltaTime()*100;
		if (variacao>=3)
			variacao=0;
	}

	private void verificarEstadoJogo (){

		/*
		0 - Estado inicial, pássaro parado
		1 - Começa o jogo
		2 - Colidiu
		 */

		Boolean toqueTela = Gdx.input.justTouched();

		switch(estadoJogo){

			case 0:
				//Evento de clique
				if (toqueTela){
					gravidade = -17;
					estadoJogo = 1;
					somVoando.play();
				}
			break;

			case 1:
				//Evento de clique
				if (toqueTela){
					gravidade = -17;
					estadoJogo = 1;
					somVoando.play();
				}

				//Movimentar o cano
				posicaoHorizontalCano -= Gdx.graphics.getDeltaTime() * 300;
				if (posicaoHorizontalCano < -canoBaixo.getWidth()){
					posicaoHorizontalCano = larguraDispositivo;
					posicaoVerticalCano = random.nextInt(800) -400;
					passouCano=false;
				}

				//Efeito de queda no pássaro
				if (posicaoInicialVerticalPassaro>0 || toqueTela)
					posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

				gravidade++;
			break;

			case 2:

				if (pontos>pontuacaoMaxima){
					pontuacaoMaxima = pontos;
					preferencias.putInteger("pontuacaoMaxima",pontuacaoMaxima);
				}

				posicaoHorizontalPassaro -= Gdx.graphics.getDeltaTime()*500;

				//Evento de clique
				if (toqueTela){
					estadoJogo = 0;
					gravidade = 0;
					pontos = 0;

					posicaoInicialVerticalPassaro = alturaDispositivo/2;
					posicaoHorizontalCano = larguraDispositivo;
					posicaoHorizontalPassaro = 0;
				}
			break;
		}


	}

	private void inicializarTexturas (){
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoTopo = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
		gameOver = new Texture("game_over.png");
	}

	private void inicializarObjetos (){
		batch = new SpriteBatch();
		random = new Random();
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo/2;
		posicaoHorizontalCano = larguraDispositivo;

		//Configuracao dos textos
        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(10);

        textoReiniciar = new BitmapFont();
        textoReiniciar.setColor(Color.GREEN);
        textoReiniciar.getData().setScale(2);

		textoMelhorPontuacao = new BitmapFont();
		textoMelhorPontuacao.setColor(Color.RED);
		textoMelhorPontuacao.getData().setScale(2);


        //Formas geométricas para colisões
		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoBaixo = new Rectangle();
		retanguloCanoTopo = new Rectangle();

		//Inicializar os sons
		somVoando = Gdx.audio.newSound(Gdx.files.internal("som_asa.wav"));
		somColisao = Gdx.audio.newSound(Gdx.files.internal("som_batida.wav"));
		somPontuacao = Gdx.audio.newSound(Gdx.files.internal("som_pontos.wav"));

		//Configurar as preferencias do objeto
		preferencias = Gdx.app.getPreferences("fappyBird");
		pontuacaoMaxima = preferencias.getInteger("pontuacaoMaxima",0);
	}
	
	@Override
	public void dispose () {

	}
}
