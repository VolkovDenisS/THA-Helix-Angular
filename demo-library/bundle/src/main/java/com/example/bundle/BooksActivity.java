package com.example.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.domain.Scope;

/**
 * @author Created by ZotovES on 07.04.2026
 * Активити получения топа книг
 */
public class BooksActivity implements Service {
    @Action(name = "GetTopBooksActivity", scope = Scope.PUBLIC)
    public void action() {
        ServiceLocator.getLogger().info("Json Converter Log");
    }
}
