package com.pocolor.notes;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.pocolor.notes.di.DI;
import com.pocolor.notes.gui.startup.StartupFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("LOGS_DIR", DI.platformSpecificAppDirs.getLogsDir().toString());

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        try {
            configurator.doConfigure(Objects.requireNonNull(Main.class.getResource("/logback.xml")));
        } catch (JoranException e) {
            throw new RuntimeException(e);
        }

        log.info("Starting application");

        javax.swing.SwingUtilities.invokeLater(StartupFrame::new);
    }
}