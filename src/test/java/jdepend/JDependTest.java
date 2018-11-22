package jdepend;

import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class JDependTest {

    private JDepend jdepend;

    @Before
    public void SetUp() throws IOException {
        PackageFilter filter = new PackageFilter();
        filter.addPackage("java.*");
        filter.addPackage("javax.*");
        jdepend = new JDepend(filter);
    }

    @Test
    public void shouldNotHaveCycles() throws IOException {
        jdepend.addDirectory("build/classes");
        jdepend.analyze();
        Assert.assertEquals(jdepend.containsCycles(), false);
    }

    @Test
    public void utilShouldNotDependBack() throws IOException {
        jdepend.addDirectory("build/classes/main/util");
        jdepend.addDirectory("build/classes/main/web");

        DependencyConstraint c = new DependencyConstraint();
        JavaPackage web = c.addPackage("web");
        JavaPackage util = c.addPackage("util");
        web.dependsUpon(util);

        jdepend.analyze();

        Assert.assertEquals("Dependency mismatch",
                true, jdepend.dependencyMatch(c));
    }

}