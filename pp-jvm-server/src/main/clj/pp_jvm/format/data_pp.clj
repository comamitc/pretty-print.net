(ns pp-jvm.format.data-pp)

;; http://nakkaya.com/2010/03/27/pretty-printing-xml-with-clojure/
(defn format-xml
  "Takes a valid input xml input and pretty prints using standard java APIs"
  [input tipe]
  (let [in (javax.xml.transform.stream.StreamSource.
            (java.io.StringReader. input))
        writer (java.io.StringWriter.)
        out (javax.xml.transform.stream.StreamResult. writer)
        transformer (.newTransformer
                     (javax.xml.transform.TransformerFactory/newInstance))]
    (.setOutputProperty transformer
                        javax.xml.transform.OutputKeys/INDENT "yes")
    (.setOutputProperty transformer
                        "{http://xml.apache.org/xslt}indent-amount" "2")
    (.setOutputProperty transformer
                        javax.xml.transform.OutputKeys/METHOD "xml")
    (.transform transformer in out)
    (-> out .getWriter .toString)))