package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import static com.badlogic.gdx.Input.Keys.G;

public class jogo extends ApplicationAdapter {

	//Texturas
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;

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
	private boolean passouCano = false;

	//Exibicao de texto
    BitmapFont textoPontuacao;
	
	@Override
	public void create () {
		//Gdx.app.log("create","jogo iniciado");
		inicializarTexturas();
		inicializarObjetos();
	}

	@Override
	public void render () {
		desenharTexturas();
		validarPontos();
		verificarEstadoJogo();
	}

	private void desenharTexturas(){

		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[(int)variacao],50,posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo,posicaoHorizontalCano,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos + posicaoVerticalCano);
		batch.draw(canoTopo,posicaoHorizontalCano,alturaDispositivo/2 + espacoEntreCanos + posicaoVerticalCano);
        textoPontuacao.draw(batch,String.valueOf(pontos),larguraDispositivo/2,alturaDispositivo-110);
		batch.end();
	}

	private void validarPontos(){
		if (posicaoHorizontalCano <40){
			if (!passouCano){
				pontos++;
				passouCano = true;
			}
		}
	}

	private void verificarEstadoJogo (){

		//Movimentar o cano
		posicaoHorizontalCano -= Gdx.graphics.getDeltaTime() * 300;
		if (posicaoHorizontalCano < -canoBaixo.getWidth()){
			posicaoHorizontalCano = larguraDispositivo;
			posicaoVerticalCano = random.nextInt(800) -400;
			passouCano=false;
		}

		//Evento de clique
		Boolean toqueTela = Gdx.input.justTouched();
		if (toqueTela){
			gravidade = -23;
		}

		//Efeito de queda no pássaro
		if (posicaoInicialVerticalPassaro>0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		//Bater de asas do pássaro
		variacao += Gdx.graphics.getDeltaTime()*100;
		if (variacao>=3)
			variacao=0;

		gravidade++;
	}

	private void inicializarTexturas (){
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoTopo = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
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
	}
	
	@Override
	public void dispose () {

	}
}
