package ao.co.isptec.aplm.binasjc_app;

public interface UserCallback
{
    void onUserReceived(User user);
    void onError(Throwable t);
}
