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
	private float variacao;
	
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

	}

	@Override
	public void render () {

		if (variacao>=3)
			variacao=0;

		batch.begin();
		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[(int)variacao],movimentoX,200);

		variacao += Gdx.graphics.getDeltaTime()*100;
		movimentoX++;
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
