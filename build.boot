(set-env!
  :version "0.1.0"
  :dependencies
  '[[org.clojure/clojure "1.7.0"]
    [clj-time "0.11.0"]
    [http-kit "2.1.19"]
    [camel-snake-kebab "0.3.2"]
    [com.amazonaws/aws-java-sdk-core "1.10.44"]
    [com.amazonaws/aws-java-sdk-cloudformation "1.10.44"]

    [midje "1.8.2"]
    [zilti/boot-midje "0.2.1-SNAPSHOT"]]

  :source-paths #{"src" "test"})

(require
  '[zilti.boot-midje :refer [midje]])

(task-options!
  pom {:project 'clj-cfn
       :version "0.1.0"})
