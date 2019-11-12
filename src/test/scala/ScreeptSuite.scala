import org.scalatest.FunSuite
import site.jans.screept
import site.jans.screept._
import scala.collection.mutable
class ScreeptSuite extends FunSuite {

  // just so I know how to use
  // test("An empty Set should have size 0") {
  //   assert(Set.empty.size == 0)
  // }

  // test("Invoking head on an empty Set should produce NoSuchElementException") {
  //   assertThrows[NoSuchElementException] {
  //     Set.empty.head
  //   }
  // }

  test("toBoolean should work with all possibilities"){
    assert(!Screept.toBoolean(0))
    assert(!Screept.toBoolean(0.0))
    assert(!Screept.toBoolean(""))
    assert(!Screept.toBoolean(null))
    assert(Screept.toBoolean(1))
    assert(Screept.toBoolean("value"))
  }
  
  test("test MathOperators operators"){
    val eval=Screept.evaluate(MathOperators.operators ++ BasicOperators.operators)(mutable.Map[String,String]()) _
    assert(eval("3 2 + 5 =")=="1")
    assert(eval("3 -2 + 1 =")=="1")
    assert(eval("-3 -2 + -5 =")=="1")
    assert(eval("-3 -2 - -1 =")=="1")
    assert(eval("-10 -2 / 5 =")=="1")
    assert(eval("-10 4 / -2.5 =")=="1")
    assert(eval("-10 -3 * 30 =")=="1")
    assert(eval("-10 2 * -20 =")=="1")
    assert(eval("10 5 >")=="1")
    assert(eval("10 10.0 >")=="0")
    assert(eval("10 5 <")=="0")
    assert(eval("10 10.0 <")=="0")
    assert(eval("10 5 >=")=="1")
    assert(eval("10 10.0 >=")=="1")
    assert(eval("10 5 <=")=="0")
    assert(eval("10 10.0 <=")=="1")

  }
  test("test BasicOperators operators"){
    val eval=Screept.evaluate(MathOperators.operators ++ BasicOperators.operators)(mutable.Map[String,String]()) _
    assert(eval("Jan name := name =")=="1")
    assert(eval("Jan name := name Jan =")=="1")
    assert(eval("True False 1 1 = ?")=="True")
    assert(eval("True False 1 2 = ?")=="False")
    assert(eval("True False ; 1 2 = ? ;")=="False")
    assert(eval("True False ( 1 2 = ) ? ")=="False")
    assert(eval("Jan name := name DEBUG =")=="1")
    assert(eval("Jan name := ; 39 age := ; DEBUG age")=="39")

  }

  test("test LogicOperators operators"){
    val eval=Screept.evaluate(LogicOperators.operators ++ BasicOperators.operators)(mutable.Map[String,String]()) _
    assert(eval("1 1 &")=="1")
    assert(eval("0 1 &")=="0")
    assert(eval("0 0 &")=="0")
    assert(eval("1 1 |")=="1")
    assert(eval("1 0 |")=="1")
    assert(eval("0 0 |")=="0")
    assert(eval("0.0 !")=="1")
    assert(eval("1.0 !")=="0")
    assert(eval("1 1 & 0 1 & |")=="1")
    assert(eval("1 1 &  ( 0 1 & ) |")=="1")
    assert(eval("DEBUG 1")=="1")
    assert(eval("( 1 1 & ) ( 0 1 & ) |")=="1")

  }
}