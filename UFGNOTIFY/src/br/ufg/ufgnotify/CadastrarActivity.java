package br.ufg.ufgnotify;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class CadastrarActivity extends Activity {
 
 private EditText txtNome;
 private EditText txtEmail;
 private EditText txtSenha;
 private Button btCadastrar;
 private String nome;
 private String email;
 private String senha;
 
@Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_cadastrar);
 
 txtSenha = (EditText) findViewById(R.id.txtNome);
 txtEmail = (EditText) findViewById(R.id.txtEmail);
 txtSenha = (EditText) findViewById(R.id.txtSenha);
 btCadastrar.setOnClickListener(new OnClickListener() {
 @Override
 public void onClick(View arg0) {
 nome = (txtNome.getText().toString());
 email = (txtEmail.getText().toString());
 senha = (txtSenha.getText().toString());
 
 }
 });
 
}
 
}
