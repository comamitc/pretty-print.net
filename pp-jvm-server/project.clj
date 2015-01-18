(defproject pp-jvm-server "0.1.0-SNAPSHOT"
  :description "pretty-printer server side for pretty-print.net"
  :url "http://pretty-print.net"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-beautify "0.1.0"]
                 [ring-refresh "0.1.1"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [cheshire "5.3.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [org.scala-lang/scala-library "2.10.4"]
                 [org.scala-lang/scala-reflect "2.10.4"]
                 [org.scalariform/scalariform_2.10 "0.1.4"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :plugins [[lein-ring "0.8.13"]
            [io.tomw/lein-scalac "0.1.2"]]
  :source-paths ["src" "src/main/clj"]
  :scala-source-path "src/main/scala"
  :scala-version "2.10.4"
  :prep-tasks ["scalac"]
  :repositories [["sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"}]
  ["sonatype-ss" {:url "http://oss.sonatype.org/content/repositories/snapshots"}]]
  :ring {:handler pp-jvm.core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
