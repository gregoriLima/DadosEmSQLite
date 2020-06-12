# DadosEmSQLite
Simple App in Java for Android that save data using SQLite.

### Features

- Using DAO Design to save data in Android DB SQLite
- Using ArrayAdapter and ListView to show data stored in DB

#### Classe UsuarioDao

```java

public class UsuarioDao extends AbstractDao<Usuario, Long> {

	//nome da tabela
    public static final String TABLENAME = "Usuario";

    /**
     * Properties of entity Usuario.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "Id", true, "_id");
        public final static Property Nome = new Property(1, String.class, "nome", false, "NOME");
        public final static Property FaixaIdade = new Property(2, String.class, "faixaIdade", false, "FAIXA_IDADE");
    }


    public UsuarioDao(DaoConfig config) {
        super(config);
    }
    
    public UsuarioDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Criando a tabela no banco de dados com CREATE TABLE */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"Usuario\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: Id
                "\"NOME\" TEXT," + // 1: nome
                "\"FAIXA_IDADE\" TEXT);"); // 2: faixaIdade
    }

    /** Removendo a tabela do banco de dados com DROP TABLE. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"Usuario\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Usuario entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String nome = entity.getNome();
        if (nome != null) {
            stmt.bindString(2, nome);
        }
 
        String faixaIdade = entity.getFaixaIdade();
        if (faixaIdade != null) {
            stmt.bindString(3, faixaIdade);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Usuario entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String nome = entity.getNome();
        if (nome != null) {
            stmt.bindString(2, nome);
        }
 
        String faixaIdade = entity.getFaixaIdade();
        if (faixaIdade != null) {
            stmt.bindString(3, faixaIdade);
        }
    }

    @Override //Lê o primeiro campo da tabela e retorna a chave primária
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override //Lêndo uma tupla dado o offset e retornando com obj Usuário
    public Usuario readEntity(Cursor cursor, int offset) {
        Usuario entity = new Usuario( //
            cursor.getLong(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nome
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // faixaIdade
        );
        return entity;
    }
     
    @Override //lê uma tupla da tabela dado o offset
    public void readEntity(Cursor cursor, Usuario entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setNome(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFaixaIdade(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Usuario entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Usuario entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Usuario entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

```



#### Images

> Main screen

![](https://images2.imgbox.com/b4/5d/vG3QCQbd_o.png)

> Spinner with age range

![](https://images2.imgbox.com/71/6d/oNnACvOO_o.png)


@ Grégori Fernandes de Lima 
