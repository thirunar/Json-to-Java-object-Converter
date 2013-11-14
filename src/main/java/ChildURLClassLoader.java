import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ChildURLClassLoader extends URLClassLoader {

    public ChildURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ChildURLClassLoader(URL[] urls) {
        super(urls);
    }

    public ChildURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
}
