package jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.hsqldb.DatabaseManager;
import org.hsqldb.Server;
import org.hsqldb.server.ServerConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class ServerWrapper {

  private static final String DATA_SOURCE_NAME = "jdbc/testDB";

  private static final String DB_NAME = "test";
  private static final String TEST_PROPERTIES = DB_NAME + ".properties";
  private static final String TEST_SCRIPT = DB_NAME + ".script";

  private Server server;
  private Context context;
  private File tempDir;

  public void start() throws NamingException, IOException {
    server = new Server();
    tempDir = File.createTempFile("hsql", "");
    tempDir.delete();
    tempDir.mkdir();

    copyFile("db/" + TEST_PROPERTIES, new File(tempDir, TEST_PROPERTIES));
    copyFile("db/" + TEST_SCRIPT, new File(tempDir, TEST_SCRIPT));

    server.setDatabaseName(0, DB_NAME);
    server.setDatabasePath(0, "file:" + tempDir.getPath().replace('\\', '/') + "/" + DB_NAME);
    server.setSilent(true);
    server.start();

    while (server.getState() != ServerConstants.SERVER_STATE_ONLINE) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    context = createContext();
    System.out.println("Server started");
  }

  public void stop() {
    System.out.println("Stopping server ...");
    DatabaseManager.closeDatabases(0);
    server.stop();
    while (server.getState() != ServerConstants.SERVER_STATE_SHUTDOWN) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    File[] files = tempDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    tempDir.delete();
    System.out.println("Server stopped");
  }

  public DataSource getDataSource() throws NamingException {
    return (DataSource) context.lookup(DATA_SOURCE_NAME);
  }

  private static Context createContext() throws NamingException {
    Hashtable<String, Object> env = new Hashtable<>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, MyInitialContextFactory.class.getName());
    return new InitialContext(env);
  }

  public static class MyInitialContextFactory implements InitialContextFactory {
    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
      return new Context() {

        private DataSource dataSource;

        @Override
        public Object lookup(Name name) throws NamingException {
          return null;
        }

        @Override
        public Object lookup(String name) throws NamingException {
          if (name.equals(DATA_SOURCE_NAME)) {
            if (dataSource == null) {
              dataSource = new DataSource(createPoolProperties());
            }

            return dataSource;
          } else {
            return null;
          }
        }

        private PoolProperties createPoolProperties() {
          PoolProperties p = new PoolProperties();
          p.setUrl("jdbc:hsqldb:hsql://localhost/test");
          p.setDriverClassName("org.hsqldb.jdbcDriver");
          p.setUsername("sa");
          p.setPassword("");
          p.setInitialSize(5);
          return p;
        }

        @Override
        public void bind(Name name, Object obj) throws NamingException {
        }

        @Override
        public void bind(String name, Object obj) throws NamingException {
        }

        @Override
        public void rebind(Name name, Object obj) throws NamingException {
        }

        @Override
        public void rebind(String name, Object obj) throws NamingException {
        }

        @Override
        public void unbind(Name name) throws NamingException {
        }

        @Override
        public void unbind(String name) throws NamingException {
        }

        @Override
        public void rename(Name oldName, Name newName) throws NamingException {
        }

        @Override
        public void rename(String oldName, String newName) throws NamingException {
        }

        @Override
        public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
          return null;
        }

        @Override
        public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
          return null;
        }

        @Override
        public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
          return null;
        }

        @Override
        public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
          return null;
        }

        @Override
        public void destroySubcontext(Name name) throws NamingException {
        }

        @Override
        public void destroySubcontext(String name) throws NamingException {
        }

        @Override
        public Context createSubcontext(Name name) throws NamingException {
          return null;
        }

        @Override
        public Context createSubcontext(String name) throws NamingException {
          return null;
        }

        @Override
        public Object lookupLink(Name name) throws NamingException {
          return null;
        }

        @Override
        public Object lookupLink(String name) throws NamingException {
          return null;
        }

        @Override
        public NameParser getNameParser(Name name) throws NamingException {
          return null;
        }

        @Override
        public NameParser getNameParser(String name) throws NamingException {
          return null;
        }

        @Override
        public Name composeName(Name name, Name prefix) throws NamingException {
          return null;
        }

        @Override
        public String composeName(String name, String prefix) throws NamingException {
          return null;
        }

        @Override
        public Object addToEnvironment(String propName, Object propVal) throws NamingException {
          return null;
        }

        @Override
        public Object removeFromEnvironment(String propName) throws NamingException {
          return null;
        }

        @Override
        public Hashtable<?,?> getEnvironment() throws NamingException {
          return null;
        }

        @Override
        public void close() throws NamingException {
        }

        @Override
        public String getNameInNamespace() throws NamingException {
          return null;
        }
      };
    }
  }

  public void copyFile(String relativePath, File targetFile) throws IOException {
    InputStream in = null;
    FileOutputStream out = null;
    try {
      in = getClass().getResourceAsStream(relativePath);
      out = new FileOutputStream(targetFile);
      byte[] buf = new byte[1024];

      int currentRead;
      while ((currentRead = in.read(buf)) != -1) {
        out.write(buf, 0, currentRead);
      }
    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }

}
