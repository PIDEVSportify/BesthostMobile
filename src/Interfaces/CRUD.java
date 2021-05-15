package Interfaces;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface CRUD <T> {
    void Diplay();
    void Add(T t) throws IOException;
    void Update(T t) throws IOException;
    void Delete(T t) throws IOException;
}
