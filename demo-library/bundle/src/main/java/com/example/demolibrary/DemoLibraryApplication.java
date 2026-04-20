/*
 * Copyright (c) 2001-2015 BMC Software, Inc. All rights reserved. This software is the confidential
 * and proprietary information of BMC Software, Inc ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement between you and BMC Software, Inc.
 */

package com.example.demolibrary;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.common.RxBundle;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.demolibrary.activity.ConvertRecordsToJsonActivity;
import com.example.demolibrary.repo.AuthorRepository;
import com.example.demolibrary.repo.BookRepository;
import com.example.demolibrary.repo.PublisherRepository;
import com.example.demolibrary.service.BookService;

/**
 * Rx Web Activator class.
 */
public class DemoLibraryApplication extends RxBundle {

    /* (non-Javadoc)
     * @see com.bmc.arsys.rx.business.common.RxBundle#register()
     */
    @Override
    protected void register() {
        RecordService recordService = ServiceLocator.getRecordService();
        AuthorRepository authorRepository = new AuthorRepository(recordService);
        PublisherRepository publisherRepository = new PublisherRepository(recordService);
        BookRepository bookRepository = new BookRepository(recordService);
        BookService bookService = new BookService(
                bookRepository, authorRepository, publisherRepository);

        registerService(new ConvertRecordsToJsonActivity(bookService));
        registerStaticWebResource(String.format("/%s", getId()), "/webapp");
    }
}
