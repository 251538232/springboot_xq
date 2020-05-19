package jp.co.xq.base.utils;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ScatterSample {

    ParallelScatterZipCreator scatterZipCreator = new ParallelScatterZipCreator();


    public ScatterSample() throws IOException {
    }

    public void addEntry(final ZipArchiveEntry zipArchiveEntry, final InputStreamSupplier streamSupplier) throws IOException {
            scatterZipCreator.addArchiveEntry(zipArchiveEntry, streamSupplier);
    }

    public void writeTo(final ZipArchiveOutputStream zipArchiveOutputStream)
            throws IOException, ExecutionException, InterruptedException {
        ScatterZipOutputStream dirs = ScatterZipOutputStream.fileBased(File.createTempFile("scatter-dirs", "tmp"));
        dirs.writeTo(zipArchiveOutputStream);
        dirs.close();
        scatterZipCreator.writeTo(zipArchiveOutputStream);
    }
}