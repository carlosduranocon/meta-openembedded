DESCRIPTION = "The gSOAP toolkit provides a unique SOAP-to-C/C++ language binding \
for the development of SOAP Web Services and clients."
SECTION = "devel"
LICENSE = "GPL-2.0-with-OpenSSL-exception"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4f40a941379143186f9602242c3fb729 \
                    file://GPLv2_license.txt;md5=a33672dbe491b6517750a0389063508b"

SRC_URI = "${SOURCEFORGE_MIRROR}/${BPN}2/${BPN}_${PV}.zip \
           "
SRC_URI[sha256sum] = "4b798780989338f665ef8e171bbcc422a271004d62d5852666d5eeca33a6a636"

inherit autotools

BBCLASSEXTEND = "native"

S = "${WORKDIR}/${BPN}-2.8"

PARALLEL_MAKE = ""

EXTRA_OEMAKE:class-target = "SOAP=${STAGING_BINDIR_NATIVE}/soapcpp2"

DEPENDS = "openssl zlib flex bison bison-native"
DEPENDS:append:class-target = " gsoap-native"

do_install:append() {
    install -d ${D}${libdir}
    for lib in libgsoapssl libgsoapssl++ libgsoap libgsoapck++ libgsoap++ libgsoapck
    do
        oe_libinstall -C gsoap $lib ${D}${libdir}
    done
}

do_install:class-native() {
    oe_runmake DESTDIR=${D} BINDIR=${D}${bindir} install
}

FILES:${PN} = "${bindir}/wsdl2h ${bindir}/soapcpp2"
FILES:${PN} += "${datadir}"
FILES:${PN}-staticdev = "${libdir}"
FILES:${PN}-dev = "${includedir}"
RRECOMMENDS:${PN}-dev = "${PN}-staticdev"
