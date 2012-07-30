updater.exe -noExit -console -noSplash -application org.eclipse.equinox.p2.director -repository  http://localhost:8980/external-bundles -u  com.tmwsystems.osgi.cdi.feature.group -destination d:/eclipse/ -profile VirgoProfile -profileProperties org.eclipse.update.install.features=true -roaming
updater.exe -noExit -console -noSplash -application org.eclipse.equinox.p2.director  -repository file:E:/downloads/p2-repositories/virgo/3.5.0.RELEASE -profile NanoFull -i nano-full.product -profileProperties org.eclipse.update.install.features=true -destination E:\virgo-installs\nano-full\
updater.exe -noExit -console -noSplash -application org.eclipse.equinox.p2.director -repository  http://localhost:8980/com.tmwsystems.cdi.sample -u  com.tmwsystems.cdi.sare.group -destination C:\Java\nano-virgo-full\
updater.exe -noExit -console -noSplash -application org.eclipse.equinox.p2.director -repository http://localhost:8980/com.tmwsystems.cdi.dependancies -i  com.tmwsystems.cdi.dependancies.feature.group -destination E:\virgo-installs\nano-full\
-p2.os win32   -p2.ws win32   -p2.arch x86_64
eclipse.exe -noExit -console -noSplash -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/virgo/updatesite/3.5.0.M04  -i nano-full.product -profileProperties org.eclipse.update.install.features=true -destination D:\nano -profile Nano
E:/downloads/p2-repositories/ibm/OSGiAppTools
http://download.eclipse.org/virgo/updatesite/3.5.0.M04
http://download.eclipse.org/virgo/updatesite/3.5.0.RELEASE
E:\downloads\p2-repositories\ibm\OSGiAppTools