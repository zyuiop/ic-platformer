-injars       ../target/icplatformer-0.0.1-SNAPSHOT.jar
-outjars      ../platformer.jar
-libraryjars  <java.home>/lib/rt.jar
-optimizationpasses 3
-overloadaggressively
-repackageclasses ''
-allowaccessmodification

-keep public class platform.Program {
    public static void main(java.lang.String[]);
}