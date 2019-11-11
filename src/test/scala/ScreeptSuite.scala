import org.scalatest.FunSuite
import site.jans.game.Screept

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
}