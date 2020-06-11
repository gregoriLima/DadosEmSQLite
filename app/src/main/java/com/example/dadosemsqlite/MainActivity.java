package com.example.dadosemsqlite;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

//imports do DB
import java.util.List;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;




public class MainActivity extends AppCompatActivity implements View.
        OnClickListener {

    private Button btnCadastrar, btnLimpar, btnRemoveAll;
    private EditText nome;

    //atributos do DB
    private DaoSession daoSession;
    private usuarioDao ObjUsuarioDao;
    private Query<usuario> usuariosQuery;
    private DaoSession daoMsgSession;
    private usuarioDao mensagemDao;

    //Lista de usuários cadastrados no SQLite
    private ListView listaUsuarios;
    private List<usuario> todosUsuarios;
    private Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //campo nome
        nome = (EditText) findViewById(R.id.txtNome);

        //Lista com as faixas etárias para o Spinner
        ArrayList<String> faixasEtarias = new ArrayList<String>();
        faixasEtarias.add("Under 18");
        faixasEtarias.add("Between 18 and 20 years");
        faixasEtarias.add("Between 20 and 25 years");
        faixasEtarias.add("Between 25 and 30 years");
        faixasEtarias.add("Between 30 and 35 years");
        faixasEtarias.add("Between 35 and 40 years");
        faixasEtarias.add("Between 40 and 45 years");
        faixasEtarias.add("Between 45 and 50 years");
        faixasEtarias.add("Between 50 and 55 years");
        faixasEtarias.add("Between 55 and 60 years");
        faixasEtarias.add("Over 60 years");

        //Spinner das faixas etárias
        s = (Spinner) findViewById(R.id.faixaSpinner);
        //Envia a ArrayList das faixas etárias para o spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, faixasEtarias);
        s.setAdapter(adapter);


        //botões da tela
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);

        btnLimpar = findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(this);

        btnRemoveAll = findViewById(R.id.btnRemoverTodos);
        btnRemoveAll.setOnClickListener(this);


        // SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "usuariosDb");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        // get usuario DAO
        ObjUsuarioDao = daoSession.getUsuarioDao();


        // initialize listview
        listaUsuarios = (ListView) findViewById(R.id.lstUsuarios);

        //busca todas os usuários cadastrados
        todosUsuarios = ObjUsuarioDao.queryBuilder().list();

        //criando uma instância do arrayAdapter, com o contexto da activity atual e o int que representa cada ítem da lista
        //e recebe a list 'todosUsuarios'
        final ArrayAdapter<usuario> adapterUsuarios = new ArrayAdapter<usuario>(this,android.R.layout.simple_list_item_1, todosUsuarios);
        //listView setando o adapter criado acima
        listaUsuarios.setAdapter(adapterUsuarios);

        //in Test
        //rotina que verifica se há permissão para acessar os contatos no celular.
        //checkPermissão();
    }

    @Override
    public void onClick(View view) {

        if(view == btnCadastrar){
            //Toast.makeText(this, "teste!", Toast.LENGTH_LONG).show();
            //verifica se o campo nome foi preenchido
            if (nome.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the name!", Toast.LENGTH_LONG).show();
            } else {

                //verifica se o usuário já existe pelo número do registro
                if (ObjUsuarioDao.queryBuilder()
                                .where(usuarioDao.Properties.Nome.eq(nome.getText().toString())).list().isEmpty()) {

                    //adiciona um usuário
                    usuario user = new usuario();
                    //um ID para cada usuário no DB
                    user.setId(todosUsuarios.isEmpty()?10:todosUsuarios.get(todosUsuarios.size() -1).getId() + 10);
                    user.setFaixaIdade(s.getSelectedItem().toString());
                    user.setNome(nome.getText().toString());

                    ObjUsuarioDao.insert(user);


                    Toast.makeText(this, "User saved successfully!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(this, "Registered user already!", Toast.LENGTH_LONG).show();
            }

        }

        if(view == btnLimpar){

            nome.setText("");
            s.setSelection(0);

        }

        if(view == btnRemoveAll){

            ObjUsuarioDao.deleteAll();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }


    }


    //Verifica permissão para ler a agenda de contatos do celular
    private boolean checkPermissão(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "É necessária permissão para ler a agenda de contatos.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 789);
                return false;
            }else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 123);
                return false;
            }
        }
        return true;
    }

}