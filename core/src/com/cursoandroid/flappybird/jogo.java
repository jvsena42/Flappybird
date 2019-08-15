package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private float espacoEntreCanos = 130;
	
	@Override
	public void create () {
		//Gdx.app.log("create","jogo iniciado");
		inicializarTexturas();
		inicializarObjetos();
	}

	@Override
	public void render () {
		desenharTexturas();
		verificarEstadoJogo();
	}

	private void desenharTexturas(){

		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[(int)variacao],40,posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo,posicaoHorizontalCano,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos);
		batch.draw(canoTopo,posicaoHorizontalCano,alturaDispositivo/2 + espacoEntreCanos );
		posicaoHorizontalCano--;

		batch.end();
	}

	private void verificarEstadoJogo (){

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
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo/2;
		posicaoHorizontalCano = larguraDispositivo;
	}
	
	@Override
	public void dispose () {

	}
}
