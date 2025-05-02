package org.example.ReaderManager;

import java.io.*;


public class Serializer {

    public static  <T> byte[] serialize(T object) throws IOException {
        if(!(object instanceof  Serializable)){
            throw  new IOException("The object is not serializable");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> T deserialize(Class<T> objectClass  ,byte[] object) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(object);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ){
            return (T) objectInputStream.readObject();
        }
        catch (IOException e) {
            throw new ClassNotFoundException("The object was no able to serialize " + e.getMessage());
        }catch (ClassNotFoundException e){
            throw new ClassNotFoundException("CLass does not exist");
        }
    }


}
