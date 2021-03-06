/*
 * ShowCaseStandalone - A Minecraft-Bukkit-API Shop Plugin
 * Copyright (C) 2016-08-16 22:43 +02 kellerkindt (Michael Watzko) <copyright at kellerkindt.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kellerkindt.scs.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.kellerkindt.scs.Properties;
import com.kellerkindt.scs.ShowCaseStandalone;
import com.kellerkindt.scs.utilities.Term;

/**
 *
 * @author michael <michael at kellerkindt.com>
 */
public class Version extends SimpleCommand {

    public Version(ShowCaseStandalone scs, String...permissions) {
        super(scs, permissions);
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        // nothing to do
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        scs.sendMessage(sender, Term.MESSAGE_VERSION_1.get());
        scs.sendMessage(sender, Term.MESSAGE_VERSION_2.get(scs.getDescription().getVersion()));
        scs.sendMessage(sender, Term.MESSAGE_VERSION_4.get(Properties.BUILD_AUTHOR));
        scs.sendMessage(sender, Term.MESSAGE_VERSION_5.get(Properties.BUILD_CONTRIBUTOR));
        
        if (scs.isAdmin(sender) && Properties.BUILD_ISDEV) {
            scs.sendMessage(sender, Term.WARNING_DEV_VERSION.get());
        }
    }
}
