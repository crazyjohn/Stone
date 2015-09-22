/*
 * ex: set ro:
 * DO NOT EDIT.
 * generated by smc (http://smc.sourceforge.net/)
 * from file : ClientSmHostContext.sm
 */

package com.stone.sm;

import com.stone.sm.host.ClientSmHost;

public class ClientSmHostContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

    public ClientSmHostContext(ClientSmHost owner)
    {
        super (ClientStateMap.Default);

        _owner = owner;
    }

    public ClientSmHostContext(ClientSmHost owner, ClientSmHostState initState)
    {
        super (initState);

        _owner = owner;
    }

    public void enterStartState()
    {
        getState().Entry(this);
        return;
    }

    public void battle()
    {
        _transition = "battle";
        getState().battle(this);
        _transition = "";
        return;
    }

    public void battleEnd()
    {
        _transition = "battleEnd";
        getState().battleEnd(this);
        _transition = "";
        return;
    }

    public void connect(String host, int port)
    {
        _transition = "connect";
        getState().connect(this, host, port);
        _transition = "";
        return;
    }

    public void enterScene(String puid, int sceneId)
    {
        _transition = "enterScene";
        getState().enterScene(this, puid, sceneId);
        _transition = "";
        return;
    }

    public void login(String puid)
    {
        _transition = "login";
        getState().login(this, puid);
        _transition = "";
        return;
    }

    public void start()
    {
        _transition = "start";
        getState().start(this);
        _transition = "";
        return;
    }

    public ClientSmHostState getState()
        throws statemap.StateUndefinedException
    {
        if (_state == null)
        {
            throw(
                new statemap.StateUndefinedException());
        }

        return ((ClientSmHostState) _state);
    }

    protected ClientSmHost getOwner()
    {
        return (_owner);
    }

    public void setOwner(ClientSmHost owner)
    {
        if (owner == null)
        {
            throw (
                new NullPointerException(
                    "null owner"));
        }
        else
        {
            _owner = owner;
        }

        return;
    }

//---------------------------------------------------------------
// Member data.
//

    transient private ClientSmHost _owner;

    public static abstract class ClientSmHostState
        extends statemap.State
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected ClientSmHostState(String name, int id)
        {
            super (name, id);
        }

        protected void Entry(ClientSmHostContext context) {}
        protected void Exit(ClientSmHostContext context) {}

        protected void battle(ClientSmHostContext context)
        {
            Default(context);
        }

        protected void battleEnd(ClientSmHostContext context)
        {
            Default(context);
        }

        protected void connect(ClientSmHostContext context, String host, int port)
        {
            Default(context);
        }

        protected void enterScene(ClientSmHostContext context, String puid, int sceneId)
        {
            Default(context);
        }

        protected void login(ClientSmHostContext context, String puid)
        {
            Default(context);
        }

        protected void start(ClientSmHostContext context)
        {
            Default(context);
        }

        protected void Default(ClientSmHostContext context)
        {
            throw (
                new statemap.TransitionUndefinedException(
                    "State: " +
                    context.getState().getName() +
                    ", Transition: " +
                    context.getTransition()));
        }

    //-----------------------------------------------------------
    // Member data.
    //
    }

    /* package */ static abstract class ClientStateMap
    {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
        // Constants.
        //
        public static final ClientStateMap_None None =
            new ClientStateMap_None("ClientStateMap.None", 0);
        public static final ClientStateMap_Connected Connected =
            new ClientStateMap_Connected("ClientStateMap.Connected", 1);
        public static final ClientStateMap_Authed Authed =
            new ClientStateMap_Authed("ClientStateMap.Authed", 2);
        public static final ClientStateMap_Game Game =
            new ClientStateMap_Game("ClientStateMap.Game", 3);
        public static final ClientStateMap_Battle Battle =
            new ClientStateMap_Battle("ClientStateMap.Battle", 4);
        private static final ClientStateMap_Default Default =
            new ClientStateMap_Default("ClientStateMap.Default", -1);

    }

    protected static class ClientStateMap_Default
        extends ClientSmHostState
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected ClientStateMap_Default(String name, int id)
        {
            super (name, id);
        }

        protected void start(ClientSmHostContext context)
        {


            (context.getState()).Exit(context);
            context.setState(ClientStateMap.None);
            (context.getState()).Entry(context);
            return;
        }
    //-----------------------------------------------------------
    // Member data.
    //
    }

    private static final class ClientStateMap_None
        extends ClientStateMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private ClientStateMap_None(String name, int id)
        {
            super (name, id);
        }

        protected void connect(ClientSmHostContext context, String host, int port)
        {
            ClientSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.log(host + ":" + port);
            }
            finally
            {
                context.setState(ClientStateMap.Connected);
                (context.getState()).Entry(context);
            }
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }

    private static final class ClientStateMap_Connected
        extends ClientStateMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private ClientStateMap_Connected(String name, int id)
        {
            super (name, id);
        }

        protected void login(ClientSmHostContext context, String puid)
        {
            ClientSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.log(String.format("%s login.", puid));
            }
            finally
            {
                context.setState(ClientStateMap.Authed);
                (context.getState()).Entry(context);
            }
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }

    private static final class ClientStateMap_Authed
        extends ClientStateMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private ClientStateMap_Authed(String name, int id)
        {
            super (name, id);
        }

        protected void enterScene(ClientSmHostContext context, String puid, int sceneId)
        {
            ClientSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.log(String.format("%s enterScene %d.", puid, sceneId));
            }
            finally
            {
                context.setState(ClientStateMap.Game);
                (context.getState()).Entry(context);
            }
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }

    private static final class ClientStateMap_Game
        extends ClientStateMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private ClientStateMap_Game(String name, int id)
        {
            super (name, id);
        }

        protected void battle(ClientSmHostContext context)
        {
            ClientSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.log("battle state.");
            }
            finally
            {
                context.setState(ClientStateMap.Battle);
                (context.getState()).Entry(context);
            }
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }

    private static final class ClientStateMap_Battle
        extends ClientStateMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private ClientStateMap_Battle(String name, int id)
        {
            super (name, id);
        }

        protected void battleEnd(ClientSmHostContext context)
        {
            ClientSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.log("battle end.");
            }
            finally
            {
                context.setState(ClientStateMap.Game);
                (context.getState()).Entry(context);
            }
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */