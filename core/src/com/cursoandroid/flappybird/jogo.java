package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Input.Keys.G;

public class jogo extends ApplicationAdapter {

	private int movimentoX = 0;
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;

	//Atributos de configurações
	private float alturaDispositivo;
	private float larguraDispositivo;
	private float variacao =0;
	private float gravidade = 0;
	private float posicaoInicialVerticalPassaro;
	
	@Override
	public void create () {
		//Gdx.app.log("create","jogo iniciado");
		batch = new SpriteBatch();
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo/2;

	}

	@Override
	public void render () {

		if (variacao>=3)
			variacao=0;

		//Evento de clique
        Boolean toqueTela = Gdx.input.justTouched();
        if (toqueTela){
            gravidade = -23;
        }

		//Efeito de queda no pássaro
        if (posicaoInicialVerticalPassaro>0 || toqueTela)
        posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		batch.begin();
		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[(int)variacao],30,posicaoInicialVerticalPassaro);

		variacao += Gdx.graphics.getDeltaTime()*100;
		gravidade++;
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
