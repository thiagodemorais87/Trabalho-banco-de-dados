''
public class CadastroApp {
    
    public static class Pessoa {
        private String nome;
        private int idade;

        public Pessoa(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
        }

        @Override
        public String toString() {
            return "Pessoa [nome=" + nome + ", idade=" + idade + "]";
        }
    }

    public static void main(String[] args) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "banco.db4o");

        Pessoa pessoa1 = new Pessoa("João", 25);
        Pessoa pessoa2 = new Pessoa("Maria", 30);
        
        db.store(pessoa1);
        db.store(pessoa2);

        System.out.println("Registros antes da atualização:");
        listarRegistros(db);

        ObjectSet<Pessoa> result = db.queryByExample(new Pessoa("João", 0));
        if (result.hasNext()) {
            Pessoa pessoaParaAtualizar = result.next();
            pessoaParaAtualizar.setIdade(26);
            db.store(pessoaParaAtualizar);
        }

    
        ObjectSet<Pessoa> pessoasParaExcluir = db.queryByExample(new Pessoa("Maria", 0));
        while (pessoasParaExcluir.hasNext()) {
            Pessoa pessoaParaExcluir = pessoasParaExcluir.next();
            db.delete(pessoaParaExcluir);
        }
        System.out.println("Registros após as operações:");
        listarRegistros(db);

        db.close();
    }

    private static void listarRegistros(ObjectContainer db) {
        ObjectSet<Pessoa> result = db.queryByExample(Pessoa.class);
        while (result.hasNext()) {
            Pessoa pessoa = result.next();
            System.out.println(pessoa);
        }
        System.out.println();
    }
}
