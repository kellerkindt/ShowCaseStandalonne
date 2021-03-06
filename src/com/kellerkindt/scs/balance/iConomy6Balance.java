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
package com.kellerkindt.scs.balance;

import com.iCo6.iConomy;
import com.iCo6.system.Account;
import com.iCo6.system.Accounts;
import com.kellerkindt.scs.ShowCaseStandalone;
import com.kellerkindt.scs.interfaces.Balance;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.function.Function;

public class iConomy6Balance extends iConomyBalance implements Balance {
    
    private iConomy  iconomy;
    private Accounts accounts;

    public iConomy6Balance(ShowCaseStandalone scs, Plugin plugin) {
        super(scs);
        this.iconomy    = (iConomy) plugin;
        this.accounts   = new Accounts();
    }

    private boolean executeIfAccountExists(String playerName, Function<Account, Boolean> function) {
        return executeIfNotNull(
                playerName,
                (name) -> {
                    Account account = accounts.get(name);
                    return account == null || account.getHoldings() == null ? null : account;
                },
                function
        );
    }

    @Override
    public boolean isActive() {
        return iconomy.isEnabled();
    }

    @Override
    public boolean exists(OfflinePlayer player, UUID playerId, String playerName) {
        return executeIfAccountExists(
                getPlayerName(player, playerId, playerName),
                (account) -> account != null
        );
    }

    @Override
    public boolean has(OfflinePlayer player, UUID playerId, String playerName, double amount) {
        return executeIfAccountExists(
                getPlayerName(player, playerId, playerName),
                (account) -> account.getHoldings().hasEnough(amount)
        );
    }

    @Override
    public boolean add(OfflinePlayer player, UUID playerId, String playerName, double amount) {
        return executeIfAccountExists(
                getPlayerName(player, playerId, playerName),
                (account) -> {
                    account.getHoldings().add(amount);
                    return true;
                }
        );
    }

    @Override
    public boolean sub(OfflinePlayer player, UUID playerId, String playerName, double amount) {
        return executeIfAccountExists(
                getPlayerName(player, playerId, playerName),
                (account) -> {
                    // you seem to have fixed your typo :P
                    account.getHoldings().subtract(amount);
                    return true;
                }
        );
    }


    @Override
    public String format(double amount) {
        return iConomy.format(amount);
    }

}
