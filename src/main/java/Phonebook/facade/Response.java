package Phonebook.facade;

import com.google.gson.JsonElement;


public class Response
{
    protected int status;
    protected String message;
    protected JsonElement data;

    public Response()
    {
        this.status=-1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}

class Error extends Response
{
    public Error(String msg)
    {
        this.status=0;
        this.message=msg;
    }
}

class Data extends Response
{
    public Data(JsonElement data)
    {
        this.status=1;
        this.data=data;
    }
}

class Notification extends Response
{
    public Notification()
    {
        this.status=1;
    }
}

