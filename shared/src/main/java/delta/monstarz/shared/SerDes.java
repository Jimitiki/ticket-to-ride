package delta.monstarz.shared;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;

public class SerDes {
    public static BaseCommand deserializeCommand(String json, String packagePrefix)
    {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(json, JsonObject.class);
        String commandname = jsonObj.get("name").getAsString();

        String classname = packagePrefix + commandname;

        BaseCommand basecmd = null;
        try {
            Class c = null;
            try {
                c = Class.forName(classname);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            basecmd = (BaseCommand)gson.fromJson(json, c);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	    if (jsonObj.has("commands")) {
		    ((CommandListCommand) basecmd).setCommands(deserializeCommandList(jsonObj.getAsJsonArray("commands")));
	    }
        return basecmd;
    }

    public static String serialize(Object c) {
        Gson gson = new Gson();
        return gson.toJson(c);
    }

    public static Args deserializeArgs(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, Args.class);
    }

	private static List<BaseCommand> deserializeCommandList(JsonArray commands) {
		List<BaseCommand> commandList = new ArrayList<>();
		for (int i = 0; i < commands.size(); i++) {
			commandList.add(deserializeCommand(commands.get(i).toString(), "deltamonstarz.tickettoride.commands.Client"));
		}
		return commandList;
	}
}